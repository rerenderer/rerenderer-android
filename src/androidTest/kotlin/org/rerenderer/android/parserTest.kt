package org.rerenderer.android

import android.test.AndroidTestCase
import com.github.salomonbrys.kotson.fromJson
import kotlin.test.assertFailsWith


class ParserTest : AndroidTestCase() {

    fun testSerializeEvent() {
        val event = Bus.Event("click", mapOf("x" to 10, "y" to 50))
        val serialized = parser.encode(event)
        assertEquals("{\"event\":\"click\",\"x\":10,\"y\":50}", serialized)
    }
}
