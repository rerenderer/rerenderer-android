package org.rerenderer.android.primitives

import org.rerenderer.android.BaseTest

class ImageTest : BaseTest() {
    fun testRegister() {
        Image.register(context)
        val img = registry["bundled.image"]!!(mapOf(), listOf(), "")
        assertTrue(img is Image)
    }

    fun testRender() {
        val img = Image(
                mapOf(
                        "width" to 800.0, "height" to 600.0,
                        "x" to 0.0, "y" to 0.0,
                        "sx" to 0.0, "sy" to 0.0,
                        "src" to "file:///android_asset/test.png"),
                listOf(), "", context)
        val bitmap = img.toBitmap()
        bitmap.assertPixel(1, 1, 255, 72, 0, 255)
    }
}