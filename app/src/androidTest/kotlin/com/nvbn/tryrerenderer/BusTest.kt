package com.nvbn.tryrerenderer

import android.test.AndroidTestCase

class BusTest : AndroidTestCase() {
    fun withBus(action: Bus.() -> Unit): String? {
        var result: String? = null
        val bus = Bus({}, { result = it })
        action(bus)
        return result
    }

    fun testInterprete() {
        var result: Bus.InterpreteRequest? = null
        val bus = Bus({ result = it }, {})
        bus.interprete("{\"scale\": true, \"root\": [\"ref\", \"test\"], \"script\": [[\"new\", [\"ref\", \"x\"], [\"static\", \"Test\"], [[\"ref\", \"y\"], [\"val\", 12]]]]}")

        assertEquals(result!!.root.id, "test")
        assertEquals(result!!.scale, true)

        val instruction = result!!.script[0] as Instruction.New
        assertEquals("x", instruction.resultRef.id)
        assertEquals("Test", (instruction.cls as Var.Static).id)
        assertEquals("y", (instruction.args[0] as Var.Ref).id)
        assertEquals(12.0, (instruction.args[1] as Var.Val).value)
    }

    fun testSendEvent() {
        val result = withBus {
            val event = Bus.Event("click", mapOf("x" to 10, "y" to 50))
            sendEvent(event)
        }
        assertEquals(
                "androidEventsCallback('{\"event\":\"click\",\"x\":10,\"y\":50}');",
                result)
    }

    fun testUpdateInformation() {
        val result = withBus { updateInformation(100, 200) }
        assertEquals("androidUpdateInformation(100, 200);", result)
    }
}