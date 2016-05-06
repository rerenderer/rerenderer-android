package org.rerenderer.android

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.test.AndroidTestCase
import org.rerenderer.android.primitives.BasePrimitive

open class BaseTest: AndroidTestCase() {
    fun Bitmap.assertPixel(x: Int, y: Int, a: Int, r: Int, g: Int, b: Int) {
        val pixel = getPixel(x, y)
        assertEquals(Color.alpha(pixel), a)
        assertEquals(Color.red(pixel), r)
        assertEquals(Color.green(pixel), g)
        assertEquals(Color.blue(pixel), b)
    }

    fun BasePrimitive.toBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(
                prop<Int>("width"),
                prop<Int>("height"),
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        render(canvas, paint)

        return bitmap
    }
}