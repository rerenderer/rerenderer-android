package org.rerenderer.android.primitives

import android.graphics.Canvas
import android.graphics.Paint
import android.test.AndroidTestCase
import kotlin.test.assertFailsWith

class FakePrimitive(props: Map<String, Any?>): BasePrimitive(props, listOf(), "") {
    override fun render(canvas: Canvas, paint: Paint) {
        throw UnsupportedOperationException()
    }
}

class BasePrimitiveTest : AndroidTestCase() {
    fun withProps(vararg props: kotlin.Pair<String, Any?>, test: FakePrimitive.() -> Unit) {
        val primitive = FakePrimitive(mapOf(*props))
        primitive.test()
    }

    fun testIntProp() = withProps("x" to 1.toDouble()) {
        assertEquals(prop<Int>("x"), 1)
        assertNull(prop<Int?>("-x"))
    }

    fun testFloatProp() = withProps("y" to 2.5) {
        assertEquals(prop<Float?>("y"), 2.5f)
        assertFailsWith<TypeCastException> {
            prop<Float>("-y")
        }
    }

    fun testColorProp() = withProps("color" to listOf(255.0, 0.0, 255.0, 0.0)) {
        val color = prop<BasePrimitive.Color>("color")
        assertEquals(color, BasePrimitive.Color(0, 255, 0, 255))
        assertNull(prop<BasePrimitive.Color?>("-color"))
    }
}