package org.rerenderer.android

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.*
import org.rerenderer.android.primitives.Rectangle


open class RerendererActivity : Activity(), AnkoLogger {
    open val url = "file:///android_asset/index.html"
    var executor: Executor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = FullscreenView(ctx)
        setContentView(view)

        registerPrimitives()
        executor = Executor(ctx, url, view)
    }

    open fun registerPrimitives() {
        Rectangle.register()
    }
}
