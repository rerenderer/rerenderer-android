package org.rerenderer.android

class Cache<T> {
    val cache = mutableMapOf<String, T>()

    fun get(key: String, getter: () -> T): T {
        if (cache[key] == null) {
            cache[key] = getter()
        }

        return cache[key]!!
    }
}