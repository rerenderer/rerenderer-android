package org.rerenderer.android.render

import android.graphics.Bitmap

class BitmapCache {
    var cache = mapOf<String, Bitmap>()
    var lastKeys = listOf<String>()

    fun get(key: String, getter: () -> Bitmap): Bitmap {
        if (cache[key] == null) {
            cache = cache.plus(key to getter())
        }

        lastKeys = lastKeys.plus(key)
        return cache[key]!!
    }

    operator fun <T> invoke(action: BitmapCache.() -> T): T {
        lastKeys = listOf()
        try {
            return action()
        } finally {
            cache = cache.filterNot({ it.key in cache.keys })
//
//            for (key in cache.keys) {
//                if (key !in lastKeys) {
//                    cache.remove(key)
//                }
//            }
        }

    }
}