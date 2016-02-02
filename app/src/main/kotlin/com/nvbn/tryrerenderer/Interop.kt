package com.nvbn.tryrerenderer

import android.content.Context
import android.webkit.WebSettings
//import com.cognitect.transit.TransitFactory
//import com.cognitect.transit.Writer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.warn
import org.jetbrains.anko.webView
import com.github.salomonbrys.kotson.simpleDeserialize
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Interop(val ctx: Context, val url: String,
              val onCall: (data: List<List<Any>>, rootId: String) -> Unit) : AnkoLogger {
    inner class JSInterface {
        fun send(serialised: String, rootId: String) {
            val data = deserialise(serialised)
            try {
                onCall(data, rootId)
            } catch (e: Exception) {
                warn("Rendering callback failed", e)
            }
        }

        fun listen(event: String, callback: String) {
            info("Start listening $event")
            callbacks = callbacks.plus(event to callback)
        }

        fun width(): Int = 1080

        fun height(): Int = 1920
    }

    var callbacks = mapOf<String, String>()
    val web = ctx.webView {
        setWillNotDraw(true)
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.javaScriptEnabled = true
        settings.defaultTextEncodingName = "utf-8"
        addJavascriptInterface(JSInterface(), "android")
        loadUrl(url)
    }

    fun serialise(data: Any): String {
//        val baos = ByteArrayOutputStream()
//        val writer: Writer<Any> = TransitFactory.writer(
//                TransitFactory.Format.JSON, baos)
//        writer.write(data)
//        return baos.toString()
        return ""
    }

    fun deserialise(data: String): List<List<Any>> {
//        val bais = ByteArrayInputStream(data.toByteArray("utf-8"))
//        val reader = TransitFactory.reader(TransitFactory.Format.JSON, bais)
//        return reader.read()
        return listOf()
    }

    fun sendEvent(event: Map<String, Any>) {
        info("Send event $event")
        val serialised = serialise(event)
        val callback = callbacks[event["type"]]
        if (callback is String) {
            web.loadUrl("javascript: document['$callback']('$serialised')")
        }
    }
}

fun Context.interop(url: String, onCall: (data: List<List<Any>>, rootId: String) -> Unit) =
        Interop(this, url, onCall)
