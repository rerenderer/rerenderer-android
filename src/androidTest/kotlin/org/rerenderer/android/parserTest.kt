package org.rerenderer.android

import android.test.AndroidTestCase
import org.rerenderer.android.primitives.BasePrimitive
import org.rerenderer.android.primitives.Rectangle


class ParserTest : AndroidTestCase() {
    val primitiveJson = "[\"bundled.rectangle\", " +
            "{\"y\": 10, \"width\": 100, \"x\": 0," +
            " \"color\": [255, 255, 0, 0], \"height\": 100}, [], " +
            "\"rectangle:{}()\"]"

    fun testEncodeEvent() {
        val event = events.PlatformEvent("click", mapOf("x" to 10, "y" to 50))
        val serialized = parser.encode(event)
        assertEquals("{\"event\":\"click\",\"x\":10,\"y\":50}", serialized)
    }

    fun testDecodePrimitive() {
        Rectangle.register()
        val primitive = parser.decode<BasePrimitive>(primitiveJson)
        assertTrue(primitive is Rectangle)
        assertEquals(primitive.path, "rectangle:{}()")
        assertEquals(primitive.props["width"], 100.0)
        assertEquals(primitive.props["height"], 100.0)
        assertEquals(primitive.props["x"], 0.0)
        assertEquals(primitive.props["y"], 10.0)
        assertEquals(primitive.props["color"], listOf(255.0, 255.0, 0.0, 0.0))
        assertEquals(primitive.childs, listOf<BasePrimitive>())
    }

    fun testDecodeListOfPrimitives() {
        val json = "[]"
        val primitives = parser.decode<List<BasePrimitive>>(json)
        assertEquals(primitives, listOf<BasePrimitive>())
    }

    fun testDecodeRenderRequest() {
        Rectangle.register()
        val json = "{\"tree\": $primitiveJson, \"scale\": true}"
        val request = parser.decode<events.RenderRequest>(json)
        assertTrue(request.tree is Rectangle)
        assertTrue(request.scale)
    }
}
