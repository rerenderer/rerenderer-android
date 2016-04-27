package org.rerenderer.android

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebSettings
import org.jetbrains.anko.*
import java.util.concurrent.Executors

class Executor(
        val ctx: Context, val cljsSideUrl: String) : AnkoLogger {

    object JSInterface {
        fun render(data: String) {
            val request = parser.decode<events.RenderRequest>(data)
            events.render(request)
        }
    }

    val webView = ctx.webView {
        setWillNotDraw(true)
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.javaScriptEnabled = true
        settings.defaultTextEncodingName = "utf-8"
        addJavascriptInterface(JSInterface, "android")
        loadUrl(cljsSideUrl)
    }

    fun execute(code: String) {
        try {
            webView.loadUrl("javascript:$code")
        } catch (e: Exception) {
            error("Can't execute js:", e)
        }
    }

    fun sendPlatformEvent(event: events.PlatformEvent) {
        val serialized = parser.encode(event)
        execute("androidEventsCallback('$serialized');")
    }

    fun updatePlatformInformation(width: Int, height: Int) {
        execute("androidUpdateInformation($width, $height);")
    }
}
