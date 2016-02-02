package com.nvbn.tryrerenderer

import android.test.AndroidTestCase

class RefTest : AndroidTestCase() {
    fun testExtractVar() {
        val ref = Var.Ref("test")
        val pool = mapOf<String, Any?>(
                "test" to 12,
                "wrong" to "wrong"
        )
        assertEquals(ref.extractVar(pool)!!, 12)
    }
}

class ValTest : AndroidTestCase() {
    fun testExtractVar() {
        val val_ = Var.Val(25)
        val pool = mapOf<String, Any?>()
        assertEquals(val_.extractVar(pool)!!, 25)
    }
}

class StaticTest : AndroidTestCase() {
    fun testExtractVar() {
        // TODO: implement
    }
}
