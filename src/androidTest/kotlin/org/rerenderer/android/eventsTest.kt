package org.rerenderer.android

import android.test.AndroidTestCase

class eventsTest: AndroidTestCase() {
    fun testEvent() {
        val event = Event<Int>()
        var emited = false
        event.on {
            assertEquals(it, 10)
            emited = true
        }
        event(10)
        assertTrue(emited)
    }
}