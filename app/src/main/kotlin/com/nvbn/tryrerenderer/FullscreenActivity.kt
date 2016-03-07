package com.nvbn.tryrerenderer

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import org.jetbrains.anko.*


class FullscreenActivity : Activity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = FullscreenView(ctx)
        setContentView(view)

        val executor = Executor(
                ctx, "http://192.168.0.105:3449", view)
    }
}
