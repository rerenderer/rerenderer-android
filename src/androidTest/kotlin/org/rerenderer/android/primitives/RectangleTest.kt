package org.rerenderer.android.primitives

import org.rerenderer.android.BaseTest

class RectangleTest : BaseTest() {
    fun testRegister() {
        Rectangle.register()
        val primitive = registry["bundled.rectangle"]!!(
                mapOf(), listOf(), "")
        assertTrue(primitive is Rectangle)
    }

    fun testRender() {
        val rectangle = Rectangle(
                mapOf(
                        "color" to listOf(0.0, 255.0, 0.0, 255.0),
                        "width" to 100.0,
                        "height" to 100.0,
                        "x" to 0.0,
                        "y" to 0.0),
                listOf(), "")
        val bitmap = rectangle.toBitmap()

        bitmap.assertPixel(50, 50, 255, 0, 255, 0)
    }
}