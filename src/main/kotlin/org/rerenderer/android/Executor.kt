package org.rerenderer.android

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebSettings
import org.jetbrains.anko.*
import java.util.concurrent.Executors

class Executor(
        val ctx: Context, val cljsSideUrl: String,
        val view: FullscreenView) : AnkoLogger {
    val bus = Bus({ root, scale ->
        view.render(root, scale)
    }, { execute(it) })
    val webView = ctx.webView {
        setWillNotDraw(true)
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.javaScriptEnabled = true
        settings.defaultTextEncodingName = "utf-8"
        addJavascriptInterface(bus, "android")
        loadUrl(cljsSideUrl)
    }

    init {
        view.bus = bus
    }

    fun execute(code: String) {
        webView.loadUrl("javascript:$code")
    }
}
