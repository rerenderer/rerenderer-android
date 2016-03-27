package org.rerenderer.android

import android.test.AndroidTestCase

class CacheTest : AndroidTestCase() {
    fun testGetExists() {
        val cache = Cache<Int>()
        cache.cache["test"] = 23
        assertEquals(23, cache.get("test") { 50 })
    }

    fun testGetCalculated() {
        val cache = Cache<Int>()
        assertEquals(50, cache.get("test") { 50 })
        assertEquals(50, cache.get("test") { 10 })
    }
}