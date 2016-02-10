package com.nvbn.tryrerenderer

import android.test.AndroidTestCase

class ReflectionTest : AndroidTestCase() {
    fun testGet() {
        val inst = TestCls()
        val value = reflection.get(inst, "attr")
        assertEquals("test", value)
    }

    fun testGetStaticIntermediate() {
        val value = reflection.get(reflection.Static("com.nvbn"), "tryrerenderer")
        assertEquals(reflection.Static("com.nvbn.tryrerenderer"), value)
    }

    fun testGetClass() {
        val value = reflection.get(
                reflection.Static("com.nvbn.tryrerenderer"), "TestCls")
        assertEquals(TestCls::class, value)
    }

    fun testGetStaticAttr() {
        val value = reflection.get(TestCls::class, "staticAttr")
        assertEquals("static-test", value)
    }

    fun testCallNumeric() {
        val inst = TestCls()
        val value = reflection.call(inst, "method", listOf(1, 2.5))
        assertEquals(3, value)
    }

    fun testCallNonNumeric() {
        val inst = TestCls()
        val value = reflection.call(inst, "method", listOf("a", "b"))
        assertEquals("a-b", value)
    }

    fun testCallMixed() {
        val inst = TestCls()
        val value = reflection.call(inst, "method", listOf("a", 5))
        assertEquals("a-5.0", value)
    }

    fun testCallUnboxed() {
        val inst = TestCls()
        val value = reflection.call(inst, "method", listOf(9, "test"))
        assertEquals("9!test", value)
    }

    fun testCallStatic() {
        val value = reflection.call(TestCls::class, "staticMethod", listOf(10, 9))
        assertEquals(19, value)
    }

    fun testNewWithoutArgs() {
        val value = reflection.new(TestCls::class, listOf())
        assertTrue(value is TestCls)
    }

    fun testNewWithArgs() {
        val value = reflection.new(TestCls::class, listOf("x")) as TestCls
        assertTrue(value is TestCls)
        assertEquals("x", value.attr)
    }
}
