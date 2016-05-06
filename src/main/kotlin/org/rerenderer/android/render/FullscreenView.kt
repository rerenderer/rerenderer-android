package org.rerenderer.android.render

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.rerenderer.android.events
import org.rerenderer.android.primitives.BasePrimitive

class FullscreenView(context: Context) : SurfaceView(context), SurfaceHolder.Callback,
        View.OnTouchListener, AnkoLogger {

    var lastRoot: BasePrimitive? = null
    var scale = true
    val paint = Paint()
    var surfaceWidth = 0
    var surfaceHeight = 0
    val renderer = Renderer(paint)

    init {
        holder.addCallback(this)
        paint.setARGB(255, 0, 0, 0)
        setOnTouchListener(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        debug("Surface created")
        render(lastRoot, scale)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        debug("Surface changed")
        surfaceWidth = width
        surfaceHeight = height
        events.PlatformInformation(width, height).emit()
        render(lastRoot, scale)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    fun render(root: BasePrimitive?, scale: Boolean) {
        if (holder != null && root != null) {
            val rootBitmap = renderer.render(root)
            val canvas = holder!!.lockCanvas()
            try {
                canvas.drawRect(
                        0.toFloat(), 0.toFloat(),
                        surfaceWidth.toFloat(), surfaceHeight.toFloat(),
                        paint)
                if (scale) {
                    val scaledBitmap = Bitmap.createScaledBitmap(
                            rootBitmap, surfaceWidth, surfaceHeight, true)
                    canvas.drawBitmap(scaledBitmap, 0.toFloat(), 0.toFloat(), paint)
                } else {
                    canvas.drawBitmap(rootBitmap, 0.toFloat(), 0.toFloat(), paint)
                }
            } finally {
                holder!!.unlockCanvasAndPost(canvas)
            }
        }
        lastRoot = root
        this.scale = scale
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        events.PlatformEvent("click", mapOf(
                "x" to event.x,
                "y" to event.y
        )).emit()
        return true
    }
}
