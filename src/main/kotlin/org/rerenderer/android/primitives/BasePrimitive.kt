package org.rerenderer.android.primitives

import android.graphics.Canvas
import android.graphics.Paint

abstract class BasePrimitive(val props: Map<String, Any?>,
                             val childs: List<BasePrimitive>,
                             val path: String) {
    data class Color(val a: Int, val r: Int, val g: Int, val b: Int)

    abstract fun render(canvas: Canvas, paint: Paint)

    fun numericProp(key: String): Double? {
        val value = props[key]
        if (value is Double) {
            return value
        } else {
            return null
        }
    }

    inline fun <reified T: Any?> prop(key: String) = when (T::class) {
        Int::class -> numericProp(key)?.toInt()
        Float::class -> numericProp(key)?.toFloat()
        java.lang.Integer::class -> numericProp(key)?.toInt()
        java.lang.Float::class -> numericProp(key)?.toFloat()
        Color::class -> {
            val propValue = props[key]
            if (propValue is List<*>) {
                val color = (propValue as List<Double>).map { it.toInt() }
                Color(color[3], color[0], color[1], color[2])
            } else {
                null
            }
        }
        else -> props[key]
    } as T
}

val registry = mutableMapOf<String, (Map<String, Any?>, List<BasePrimitive>, String) -> BasePrimitive>()
