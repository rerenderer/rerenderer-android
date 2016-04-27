package org.rerenderer.android

//import android.test.AndroidTestCase
//
//class BusTest : AndroidTestCase() {
//    fun withBus(action: Bus.() -> Unit): String? {
//        var result: String? = null
//        val bus = Bus({}, { result = it })
//        action(bus)
//        return result
//    }
//
//    fun testSendEvent() {
//        val result = withBus {
//            val event = Bus.Event("click", mapOf("x" to 10, "y" to 50))
//            sendEvent(event)
//        }
//        assertEquals(
//                "androidEventsCallback('{\"event\":\"click\",\"x\":10,\"y\":50}');",
//                result)
//    }
//
//    fun testUpdateInformation() {
//        val result = withBus { updateInformation(100, 200) }
//        assertEquals("androidUpdateInformation(100, 200);", result)
//    }
//}