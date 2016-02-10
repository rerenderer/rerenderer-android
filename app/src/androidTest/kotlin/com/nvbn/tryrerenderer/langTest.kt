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

class NewTest : AndroidTestCase() {
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

class CallTest : AndroidTestCase() {
    fun testInterprete() {
        val instruction = Instruction.Call(
                Var.Ref("x"), Var.Ref("y"), "method",
                listOf(Var.Ref("z"), Var.Val(5)))
        val pool = mapOf(
                "y" to TestCls(), "z" to 12)
        val resultPool = instruction.interprete(pool)
        assertEquals(17, resultPool["x"])
    }

    fun testInterpreteStatic() {
        val instruction = Instruction.Call(
                Var.Ref("x"), Var.Ref("y"), "staticMethod",
                listOf(Var.Ref("z"), Var.Val(5)))
        val pool = mapOf(
                "y" to TestCls::class, "z" to 12)
        val resultPool = instruction.interprete(pool)
        assertEquals(17, resultPool["x"])
    }
}

class GetTest : AndroidTestCase() {
    fun testInterprete() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "attr")
        val pool = mapOf("y" to TestCls())
        val resultPool = instruction.interprete(pool)
        assertEquals(resultPool["x"], "test")
    }

    fun testInterpreteStatic() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "staticAttr")
        val pool = mapOf("y" to TestCls::class)
        val resultPool = instruction.interprete(pool)
        assertEquals(resultPool["x"], "static-test")
    }

    fun testInterpreteClass() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "TestCls")
        val pool = mapOf("y" to reflection.Static("com.nvbn.tryrerenderer"))
        val resultPool = instruction.interprete(pool)
        assertEquals(resultPool["x"], TestCls::class)
    }

    fun testInterpreteIntermediate() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "tryrerenderer")
        val pool = mapOf("y" to reflection.Static("com.nvbn"))
        val resultPool = instruction.interprete(pool)
        assertEquals(resultPool["x"], reflection.Static("com.nvbn.tryrerenderer"))
    }
}

class FreeTest : AndroidTestCase() {
    fun testInterprete() {
        val instruction = Instruction.Free(Var.Ref("x"))
        val pool = mapOf("x" to 23)
        val resultPool = instruction.interprete(pool)
        assertEquals(resultPool["x"], null)
    }
}
