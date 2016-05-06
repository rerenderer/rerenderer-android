package org.rerenderer.android.primitives

import android.graphics.Canvas
import android.graphics.Paint

class Rectangle(props: Map<String, Any?>,
                childs: List<BasePrimitive>,
                path: String) : BasePrimitive(props, childs, path) {
    override fun render(canvas: Canvas, paint: Paint) {
        val color = prop<Color>("color")
        paint.setARGB(color.a, color.r, color.g, color.b)
        canvas.drawRect(0f, 0f, prop("width"), prop("height"), paint)
    }

    companion object {
        fun register() {
            registry["bundled.rectangle"] = ::Rectangle
        }
    }
}
