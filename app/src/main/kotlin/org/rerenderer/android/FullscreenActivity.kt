package org.rerenderer.android

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.*


open class FullscreenActivity : Activity(), AnkoLogger {
    open val url = "file:///android_asset/index.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = FullscreenView(ctx)
        setContentView(view)

        val executor = Executor(ctx, url, view)
    }
}
