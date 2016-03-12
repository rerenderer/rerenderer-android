package org.rerenderer.android

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
    fun testInterpret() {
        val instruction = Instruction.New(
                Var.Ref("x"), Var.Ref("y"), listOf())
        val pool = mapOf("y" to TestCls::class)
        val resultPool = instruction.interpret(pool)
        assertTrue(resultPool["x"] is TestCls)
    }

    fun testInterpretWithArgs() {
        val instruction = Instruction.New(
                Var.Ref("x"), Var.Ref("y"), listOf(Var.Val("value")))
        val pool = mapOf("y" to TestCls::class)
        val resultPool = instruction.interpret(pool)
        assertTrue(resultPool["x"] is TestCls)
        assertEquals("value", (resultPool["x"] as TestCls).attr)
    }
}

class CallTest : AndroidTestCase() {
    fun testInterpret() {
        val instruction = Instruction.Call(
                Var.Ref("x"), Var.Ref("y"), "method",
                listOf(Var.Ref("z"), Var.Val(5)))
        val pool = mapOf(
                "y" to TestCls(), "z" to 12)
        val resultPool = instruction.interpret(pool)
        assertEquals(17, resultPool["x"])
    }

    fun testInterpretStatic() {
        val instruction = Instruction.Call(
                Var.Ref("x"), Var.Ref("y"), "staticMethod",
                listOf(Var.Ref("z"), Var.Val(5)))
        val pool = mapOf(
                "y" to TestCls::class, "z" to 12)
        val resultPool = instruction.interpret(pool)
        assertEquals(17, resultPool["x"])
    }
}

class GetTest : AndroidTestCase() {
    fun testInterpret() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "attr")
        val pool = mapOf("y" to TestCls())
        val resultPool = instruction.interpret(pool)
        assertEquals(resultPool["x"], "test")
    }

    fun testInterpretStatic() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "staticAttr")
        val pool = mapOf("y" to TestCls::class)
        val resultPool = instruction.interpret(pool)
        assertEquals(resultPool["x"], "static-test")
    }

    fun testInterpretClass() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "TestCls")
        val pool = mapOf("y" to reflection.Static("org.rerenderer.android"))
        val resultPool = instruction.interpret(pool)
        assertEquals(resultPool["x"], TestCls::class)
    }

    fun testInterpretIntermediate() {
        val instruction = Instruction.Get(
                Var.Ref("x"), Var.Ref("y"), "android")
        val pool = mapOf("y" to reflection.Static("org.rerenderer"))
        val resultPool = instruction.interpret(pool)
        assertEquals(resultPool["x"], reflection.Static("org.rerenderer.android"))
    }
}

class FreeTest : AndroidTestCase() {
    fun testInterpret() {
        val instruction = Instruction.Free(Var.Ref("x"))
        val pool = mapOf("x" to 23)
        val resultPool = instruction.interpret(pool)
        assertEquals(resultPool["x"], null)
    }
}