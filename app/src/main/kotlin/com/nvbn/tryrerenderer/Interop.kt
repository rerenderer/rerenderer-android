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

class Interop(url: String,
              val onCall: (data: Collection<List<Any>>, rootId: String) -> Unit,
              context: Context) : WebView(context) {
    val TAG = "INTEROP"
    var callbacks = mapOf<String, String>()

    inner class JSInterface {
        @JavascriptInterface
        fun send(serialised: String, rootId: String) {
            val data = deserialise(serialised)
            onCall(data, rootId)
        }

        @JavascriptInterface
        fun listen(event: String, callback: String) {
            Log.d(TAG, "Start listening $event")
            callbacks = callbacks.plus(event to callback)
        }

        @JavascriptInterface
        fun width(): Int = 1080

        @JavascriptInterface
        fun height(): Int = 1920
    }

    init {
        setWillNotDraw(true)
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.javaScriptEnabled = true
        settings.defaultTextEncodingName = "utf-8"
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
        Log.d(TAG, "Send event $event")
        val serialised = serialise(event)
        val callback = callbacks.getRaw(event.getRaw("type"))
        if (callback is String) {
            evaluateJavascript("document['$callback']('$serialised')",
                    { Log.d(TAG, it) })
        }
    }
}
