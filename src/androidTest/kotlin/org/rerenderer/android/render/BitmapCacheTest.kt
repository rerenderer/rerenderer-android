package org.rerenderer.android.render

import android.graphics.Bitmap
import android.test.AndroidTestCase

class BitmapCacheTest : AndroidTestCase() {
    fun testGet() {
        val cache = BitmapCache()
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        assertEquals(cache.get("test") { bitmap }, bitmap)
        assertEquals(cache.get("test") {
            Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        }, bitmap)
    }

    fun testSession() {
        val cache = BitmapCache()
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val secondBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        cache<Any?> {
            assertEquals(get("test") { bitmap }, bitmap)
            assertEquals(get("test") { secondBitmap }, bitmap)
        }
        cache<Any?> {
            assertEquals(get("test") { bitmap }, bitmap)
            assertEquals(get("test") { secondBitmap }, bitmap)
        }
        cache<Any?> {}
        cache<Any?> {
            assertEquals(get("test") { secondBitmap }, secondBitmap)
        }
    }
}