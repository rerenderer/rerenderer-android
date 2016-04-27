package org.rerenderer.android.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import org.rerenderer.android.primitives.BasePrimitive

class Renderer(val paint: Paint) {
    val cache = BitmapCache()

    fun renderPrimitive(primitive: BasePrimitive): Bitmap = cache.get(primitive.path) {
        val bitmap = Bitmap.createBitmap(
                (primitive.props["width"] as Double).toInt(),
                (primitive.props["height"] as Double).toInt(),
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        primitive.render(canvas, paint)
        for (child in primitive.childs) {
            val childBitmap = renderPrimitive(child)
            canvas.drawBitmap(
                    childBitmap,
                    (child.props["x"] as Double).toFloat(),
                    (child.props["y"] as Double).toFloat(),
                    paint)
        }
        bitmap
    }

    fun render(root: BasePrimitive): Bitmap = cache {
        renderPrimitive(root)
    }
}
