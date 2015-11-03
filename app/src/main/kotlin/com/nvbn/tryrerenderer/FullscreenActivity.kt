package com.nvbn.tryrerenderer

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import org.jetbrains.anko.*


public class FullscreenActivity : Activity() {
    val TAG = "FULSCREEN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val interpreter = Interpreter()
        val view = FullscreenView(ctx)
        setContentView(view)

        val interop = Interop(
                "file:///android_asset/index.html",
                { script, rootId ->
                    try {
                        view.render(interpreter.call(script, rootId))
                    } catch (e: Stopped) {
                    } catch (e: Exception) {
                        Log.e(TAG, "Interpretation failed", e)
                    }
                },
                ctx)
        view.propagate = { x -> interop.sendEvent(x) }
    }
}
