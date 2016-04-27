package org.rerenderer.android.primitives

import android.graphics.Canvas
import android.graphics.Paint

class Rectangle(props: Map<String, Any?>,
                childs: List<BasePrimitive>,
                path: String) : BasePrimitive(props, childs, path) {
    override fun render(canvas: Canvas) {
        val paint = Paint()
        if (props["color"] is List<*>) {
            val color = (props["color"] as List<Any>).map {
                (it as Double).toInt()
            }
            paint.setARGB(color[3], color[0], color[1], color[2])
        }
        canvas.drawRect(
                0f, 0f, (props["width"] as Double).toFloat(),
                (props["height"] as Double).toFloat(), paint)
    }

    companion object {
        fun register() {
            registry["bundled.rectangle"] = ::Rectangle
        }
    }
}
