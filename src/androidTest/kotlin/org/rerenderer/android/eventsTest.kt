package org.rerenderer.android

import android.test.AndroidTestCase

data class TestEvent(val x: Int, val y: Int) {
    companion object : Event<TestEvent>()

    fun emit() = Companion.emit(this)
}

class eventsTest : AndroidTestCase() {
    fun testEvent() {
        var called = false
        TestEvent on {
            called = true
            assertEquals(it.x, 10)
            assertEquals(it.y, 50)
        }

        TestEvent(10, 50).emit()
        assertTrue(called)
    }
}