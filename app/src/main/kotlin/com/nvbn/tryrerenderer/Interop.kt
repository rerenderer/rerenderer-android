package com.nvbn.tryrerenderer

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import com.cognitect.transit.TransitFactory
import com.cognitect.transit.Writer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Interop(url: String, val onCall: (data: Collection<List<Any>>, rootId: String) -> Unit,
              context: Context) : WebView(context) {
    val TAG = "INTEROP"

    inner class JSInterface {
        @JavascriptInterface
        fun send(serialised: String, rootId: String) {
            val data = deserialise(serialised)
            onCall(data, rootId)
        }

        @JavascriptInterface
        fun listen(event: String, callback: Any) {
            Log.d(TAG, callback.toString())
        }

        @JavascriptInterface
        fun width(): Int = 1080

        @JavascriptInterface
        fun height(): Int = 1920
    }

    init {
        setWillNotDraw(true)
        val settings = getSettings()
        settings.setAppCacheEnabled(false)
        settings.setAppCacheEnabled(false)
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE)
        settings.setJavaScriptEnabled(true)
        settings.setDefaultTextEncodingName("utf-8")
        val jsInterface = JSInterface()
        addJavascriptInterface(jsInterface, "android")
        loadUrl(url)
    }

    fun serialise(data: Any): String {
        val baos = ByteArrayOutputStream()
        val writer: Writer<Any> = TransitFactory.writer(
                TransitFactory.Format.JSON, baos)
        writer.write(data)
        return writer.toString()
    }

    fun deserialise(data: String): Collection<List<Any>> {
        val bais = ByteArrayInputStream(data.toByteArray("utf-8"))
        val reader = TransitFactory.reader(TransitFactory.Format.JSON, bais)
        return reader.read()
    }

    fun sendEvent(event: Map<String, Any>) {
        val serialised = serialise(event)
        evaluateJavascript("onAndroidEvent($serialised);",
                { result -> Log.d(TAG, result) })
    }
}
