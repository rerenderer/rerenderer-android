package com.nvbn.tryrerenderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.util.Log
import android.view.*

class FullscreenView(context: Context) : SurfaceView(context), SurfaceHolder.Callback,
        View.OnTouchListener {

    var lastRoot: Bitmap? = null
    val paint = Paint()
    val TAG = "FULLSCREEN_VIEW"
    var surfaceWidth = 0
    var surfaceHeight = 0
    var propagate: (event: Map<String, Any>) -> Unit = { event ->
        Log.d(TAG, "Event bus not ready, skip $event")
    }

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
                val scaledBitmap = Bitmap.createScaledBitmap(
                        rootBitmap, surfaceWidth, surfaceHeight, true)
                canvas.drawBitmap(scaledBitmap, 0.toFloat(), 0.toFloat(), paint)
            } finally {
                holder!!.unlockCanvasAndPost(canvas)
            }
        }
        lastRoot = rootBitmap
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        propagate(mapOf("type" to "click",
                "x" to event.x,
                "y" to event.y))
        return true
    }
}
