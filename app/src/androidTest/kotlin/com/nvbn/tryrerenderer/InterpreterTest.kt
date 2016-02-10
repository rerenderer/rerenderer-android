package com.nvbn.tryrerenderer

import android.test.AndroidTestCase

class InterpreterTest : AndroidTestCase() {
    fun testInterprete() {
        val script = listOf(
                Instruction.Get(Var.Ref("a"), Var.Static("com"), "nvbn"),
                Instruction.Get(Var.Ref("b"), Var.Ref("a"), "tryrerenderer"),
                Instruction.Get(Var.Ref("c"), Var.Ref("b"), "TestCls"),
                Instruction.New(Var.Ref("d"), Var.Ref("c"), listOf(Var.Val("arg"))),
                Instruction.Call(Var.Ref("e"), Var.Ref("d"), "method", listOf(Var.Val(1), Var.Val(2))),
                Instruction.Call(Var.Ref("f"), Var.Ref("c"), "staticMethod", listOf(Var.Val(3), Var.Val(4))),
                Instruction.Get(Var.Ref("g"), Var.Ref("d"), "attr"),
                Instruction.Get(Var.Ref("h"), Var.Ref("c"), "staticAttr"),
                Instruction.Free(Var.Ref("a")),
                Instruction.Free(Var.Ref("b"))
        )
        val pool = Interpreter().interprete(script)

        assertEquals(TestCls::class, pool["c"])
        assertTrue(pool["d"] is TestCls)
        assertEquals(pool["e"], 3)
        assertEquals(pool["f"], 7)
        assertEquals(pool["g"], "arg")
        assertEquals(pool["h"], "static-test")
    }
}
