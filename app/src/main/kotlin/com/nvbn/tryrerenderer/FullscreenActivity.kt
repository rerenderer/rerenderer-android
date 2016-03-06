package com.nvbn.tryrerenderer

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import org.jetbrains.anko.*


class FullscreenActivity : Activity() {
    val TAG = "FULSCREEN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val interpreter = Interpreter()
        val view = FullscreenView(ctx)
        setContentView(view)

        val interop = interop("file:///android_asset/index.html") { script, rootId ->
            try {
                interpreter.interprete(script)
                view.render(interpreter.pool[rootId] as Bitmap)
            } catch (e: Exception) {
                Log.e(TAG, "Interpretation failed", e)
            }
        }
        view.propagate = { x -> interop.sendEvent(x) }
    }
}
