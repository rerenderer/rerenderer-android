package org.rerenderer.android.primitives

import org.rerenderer.android.BaseTest

class TextTest : BaseTest() {
    fun testRegister() {
        Text.register()
        val txt = registry["bundled.text"]!!(mapOf(), listOf(), "")
        assertTrue(txt is Text)
    }

    fun testRender() {
        val txt = Text(
                mapOf(
                        "width" to 300.0, "height" to 500.0,
                        "color" to listOf(0.0, 0.0, 255.0, 255.0),
                        "x" to 0.0, "y" to 0.0,
                        "font-size" to 20.0,
                        "value" to "TESTEXT"),
                listOf(), "")
        val bitmap = txt.toBitmap()
        bitmap.assertPixel(1, 1, 0, 0, 0, 0)
        bitmap.assertPixel(1, 5, 255, 0, 0, 255)
    }
}