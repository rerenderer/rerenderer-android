package com.nvbn.tryrerenderer

import android.test.AndroidTestCase

class VarRefTest : AndroidTestCase() {
    fun testExtractVar() {
        val ref = Var.Ref("test")
        val pool = mapOf<String, Any?>(
                "test" to 12,
                "wrong" to "wrong")
        assertEquals(ref.extractVar(pool)!!, 12)
    }
}

class VarValTest : AndroidTestCase() {
    fun testExtractVar() {
        val val_ = Var.Val(25)
        val pool = mapOf<String, Any?>()
        assertEquals(val_.extractVar(pool)!!, 25)
    }
}

class VarStaticTest : AndroidTestCase() {
    fun testExtractVar() {
        val static = Var.Static("test")
        val pool = mapOf<String, Any?>()
        assertEquals(static.extractVar(pool), reflection.Static("test"))
    }
}

class NetTest: AndroidTestCase() {
    fun testInterprete() {
        val instruction = Instruction.New(
                Var.Ref("x"), Var.Ref("y"), listOf())
        val pool = mapOf("y" to TestCls::class)
        val resultPool = instruction.interprete(pool)
        assertTrue(resultPool["x"] is TestCls)
    }

    fun testInterpreteWithArgs() {
        val instruction = Instruction.New(
                Var.Ref("x"), Var.Ref("y"), listOf(Var.Val("value")))
        val pool = mapOf("y" to TestCls::class)
        val resultPool = instruction.interprete(pool)
        assertTrue(resultPool["x"] is TestCls)
        assertEquals("value", (resultPool["x"] as TestCls).attr)
    }
}
