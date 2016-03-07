package com.nvbn.tryrerenderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

class FullscreenView(context: Context) : SurfaceView(context), SurfaceHolder.Callback,
        View.OnTouchListener, AnkoLogger {

    var lastRoot: Bitmap? = null
    val paint = Paint()
    var surfaceWidth = 0
    var surfaceHeight = 0
    var bus: Bus? = null

    init {
        holder.addCallback(this)
        paint.setARGB(255, 0, 0, 0)
        setOnTouchListener(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        debug("Surface created")
        RerendererLoader.context = context
        render(lastRoot, true)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        debug("Surface changed")
        surfaceWidth = width
        surfaceHeight = height
        bus?.updateInformation(width, height)
        render(lastRoot, true)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    fun render(rootBitmap: Bitmap?, scale: Boolean) {
        debug("Render $rootBitmap on $holder")
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
        bus?.sendEvent(Bus.Event("click", mapOf(
                "x" to event.x,
                "y" to event.y
        )))
        return true
    }
}
