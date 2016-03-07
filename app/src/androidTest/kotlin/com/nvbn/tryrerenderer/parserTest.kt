package com.nvbn.tryrerenderer

import android.test.AndroidTestCase
import com.github.salomonbrys.kotson.fromJson
import kotlin.test.assertFailsWith


class ParserTest : AndroidTestCase() {

    inline fun <reified T : Any> throughJson(vararg data: Any): T {
        val json = parser.gson.toJson(data)
        if (Var::class.nestedClasses.contains(T::class)) {
            return parser.decode<Var>(json) as T
        } else {
            return parser.decode<Instruction>(json) as T
        }
    }

    // Variables:
    fun testParseRef() {
        val ref = throughJson<Var.Ref>("ref", "test")
        assertEquals(ref.id, "test")
    }

    fun testParseValDouble() {
        val val_ = throughJson<Var.Val>("val", 23)
        assertEquals(val_.value, 23.toDouble())
    }

    fun testParseValString() {
        val val_ = throughJson<Var.Val>("val", "test")
        assertEquals(val_.value, "test")
    }

    fun testParseValBoolean() {
        val val_ = throughJson<Var.Val>("val", true)
        assertEquals(val_.value, true)
    }

    fun testParseStatic() {
        val static = throughJson<Var.Static>("static", "test")
        assertEquals(static.id, "test")
    }

    fun testParseNotAllowedVar() {
        assertFailsWith(parser.NotAllowedVarException::class) {
            parser.gson.fromJson<Var>("[\"undefined\",\"test\"]")
        }
    }

    // Instructions:
    fun testParseNewWithStaticCls() {
        val new = throughJson<Instruction.New>(
                "new",
                listOf("ref", "x"),
                listOf("static", "Bitmap"),
                listOf(listOf("val", 23), listOf("ref", "test")))

        assertEquals(new.resultRef.id, "x")
        assertEquals((new.cls as Var.Static).id, "Bitmap")
        assertEquals(new.args.count(), 2)
        assertEquals((new.args[0] as Var.Val).value, 23.toDouble())
        assertEquals((new.args[1] as Var.Ref).id, "test")
    }

    fun testParseNewWithRefCls() {
        val new = throughJson<Instruction.New>(
                "new",
                listOf("ref", "x"),
                listOf("ref", "y"),
                listOf(listOf("val", 23), listOf("ref", "test")))

        assertEquals((new.cls as Var.Ref).id, "y")
    }

    fun testParseCallWithStaticObj() {
        val call = throughJson<Instruction.Call>(
                "call",
                listOf("ref", "z"),
                listOf("static", "Config"),
                "init",
                listOf(listOf("val", 10), listOf("ref", "m")))

        assertEquals(call.resultRef.id, "z")
        assertEquals((call.obj as Var.Static).id, "Config")
        assertEquals(call.method, "init")
        assertEquals(call.args.count(), 2)
        assertEquals((call.args[0] as Var.Val).value, 10.toDouble())
        assertEquals((call.args[1] as Var.Ref).id, "m")
    }

    fun testParseCallWithRefObj() {
        val call = throughJson<Instruction.Call>(
                "call",
                listOf("ref", "z"),
                listOf("ref", "q"),
                "init",
                listOf(listOf("val", 10), listOf("ref", "m")))

        assertEquals((call.obj as Var.Ref).id, "q")
    }

    fun testParseGetWithStaticObj() {
        val get = throughJson<Instruction.Get>(
                "get",
                listOf("ref", "r"),
                listOf("static", "Canvas"),
                "colors")

        assertEquals(get.resultRef.id, "r")
        assertEquals((get.obj as Var.Static).id, "Canvas")
        assertEquals(get.attr, "colors")
    }

    fun testParseGetWithRefObj() {
        val get = throughJson<Instruction.Get>(
                "get",
                listOf("ref", "r"),
                listOf("ref", "c"),
                "colors")

        assertEquals((get.obj as Var.Ref).id, "c")
    }

    fun testParseFree() {
        val free = throughJson<Instruction.Free>(
                "free",
                listOf("ref", "unned"))

        assertEquals(free.ref.id, "unned")
    }

    fun testParseInterpreteRequest() {
        val request = parser.decode<Bus.InterpreteRequest>(
                "{\"scale\": true, \"root\": [\"ref\", \"test\"], \"script\": []}")

        assertEquals(listOf<Instruction>(), request.script)
        assertEquals("test", request.root.id)
        assertEquals(true, request.scale)
    }

    fun testSerializeEvent() {
        val event = Bus.Event("click", mapOf("x" to 10, "y" to 50))
        val serialized = parser.encode(event)
        assertEquals("{\"event\":\"click\",\"x\":10,\"y\":50}", serialized)
    }

    fun testParseNotAllowedInstruction() {
        assertFailsWith(parser.NotAllowedInstructionException::class) {
            parser.decode<Instruction>("[\"undefined\",\"instruction\"]")
        }
    }
}
