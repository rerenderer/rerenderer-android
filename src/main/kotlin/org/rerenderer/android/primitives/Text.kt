package org.rerenderer.android.primitives

import android.graphics.Canvas
import android.graphics.Paint

class Text(props: Map<String, Any?>,
           childs: List<BasePrimitive>,
           path: String) : BasePrimitive(props, childs, path) {
    fun getY(paint: Paint): Float {
        val height = paint.fontMetrics.descent - paint.fontMetrics.ascent
        val y = Math.ceil(height.toDouble()) - paint.fontMetrics.descent
        return y.toFloat()
    }

    override fun render(canvas: Canvas, paint: Paint) {
        val color = prop<Color>("color")
        paint.setARGB(color.a, color.r, color.g, color.b)
        paint.textSize = prop("font-size")
        canvas.drawText(prop("value"), 0f, getY(paint), paint)
    }

    companion object {
        fun register() {
            registry["bundled.text"] = ::Text
        }
    }
}