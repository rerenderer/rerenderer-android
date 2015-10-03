package com.nvbn.tryrerenderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.*
import org.jetbrains.anko.holder

class FullscreenView(context: Context) : SurfaceView(context), SurfaceHolder.Callback,
        View.OnTouchListener {

    var lastRoot: Bitmap? = null
    val paint = Paint()
    val TAG = "FULLSCREEN_VIEW"
    var surfaceWidth = 0
    var surfaceHeight = 0
    var propagate = {(x: Map<String, Any>) -> Unit}

    init {
        getHolder().addCallback(this)
        paint.setARGB(255, 0, 0, 0)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "Surface created")
        render(lastRoot)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(TAG, "Surface changed")
        surfaceWidth = width
        surfaceHeight = height
        render(lastRoot)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    fun render(rootBitmap: Bitmap?) {
        Log.d(TAG, "Render $rootBitmap on $holder")
        if (holder != null && rootBitmap != null) {
            val canvas = holder!!.lockCanvas()
            try {
                canvas.drawRect(
                        0.toFloat(), 0.toFloat(),
                        surfaceWidth.toFloat(), surfaceHeight.toFloat(),
                        paint)
                canvas.drawBitmap(rootBitmap, 0.toFloat(), 0.toFloat(), paint)
            } finally {
                holder!!.unlockCanvasAndPost(canvas)
            }
        }
        lastRoot = rootBitmap
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        propagate(mapOf(
                "type" to "touch",
                "x" to event.getX(),
                "y" to event.getY()))
        return true
    }
}
