package org.rerenderer.android.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import org.rerenderer.android.primitives.BasePrimitive

class Renderer(val paint: Paint) {
    val cache = BitmapCache()

    fun renderPrimitive(primitive: BasePrimitive): Bitmap = cache.get(primitive.path) {
        val bitmap = Bitmap.createBitmap(
                primitive.prop<Int>("width"),
                primitive.prop<Int>("height"),
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        primitive.render(canvas, paint)
        for (child in primitive.childs) {
            val childBitmap = renderPrimitive(child)
            canvas.drawBitmap(
                    childBitmap,
                    child.prop<Float>("x"),
                    child.prop<Float>("y"),
                    paint)
        }
        bitmap
    }

    fun render(root: BasePrimitive): Bitmap = cache {
        renderPrimitive(root)
    }
}
