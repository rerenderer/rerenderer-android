package com.nvbn.tryrerenderer

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebSettings
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.webView

class Executor(
        val ctx: Context, val cljsSideUrl: String,
        val view: FullscreenView) : AnkoLogger {
    val bus = Bus({ interprete(it) }, { execute(it) })
    val interpreter = Interpreter()
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

    fun interprete(request: Bus.InterpreteRequest) {
        try {
            val pool = interpreter.interprete(request.script)
            val rootBitmap = pool[request.root.id] as Bitmap
            view.render(rootBitmap, request.scale)
        } catch (e: Exception) {
            error("Interpretation failed", e)
        }
    }
}
