package org.rerenderer.android.primitives

import android.graphics.Canvas

abstract class BasePrimitive(val props: Map<String, Any?>,
                             val childs: List<BasePrimitive>,
                             val path: String) {
    abstract fun render(canvas: Canvas)
}

val registry = mutableMapOf<String, (Map<String, Any?>, List<BasePrimitive>, String) -> BasePrimitive>()
