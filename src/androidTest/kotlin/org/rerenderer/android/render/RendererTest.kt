package org.rerenderer.android.render

import android.graphics.Paint
import org.rerenderer.android.BaseTest
import org.rerenderer.android.primitives.Rectangle

class RendererTest : BaseTest() {
    fun testRender() {
        val paint = Paint()
        val renderer = Renderer(paint)
        val primitive = Rectangle(
                mapOf(
                        "width" to 100.toDouble(),
                        "height" to 100.toDouble(),
                        "x" to 0.toDouble(),
                        "y" to 0.toDouble(),
                        "color" to listOf(
                                255.toDouble(), 0.toDouble(),
                                0.toDouble(), 255.toDouble())),
                listOf(), "path::")
        val bitmap = renderer.render(primitive)
        assertEquals(bitmap.width, 100)
        assertEquals(bitmap.height, 100)

        bitmap.assertPixel(10, 10, 255, 255, 0, 0)
    }
}