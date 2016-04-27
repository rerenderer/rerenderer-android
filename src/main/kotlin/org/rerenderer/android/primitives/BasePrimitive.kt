package org.rerenderer.android.primitives

import android.graphics.Canvas
import android.graphics.Paint

abstract class BasePrimitive(val props: Map<String, Any?>,
                             val childs: List<BasePrimitive>,
                             val path: String) {
    abstract fun render(canvas: Canvas, paint: Paint)
}

val registry = mutableMapOf<String, (Map<String, Any?>, List<BasePrimitive>, String) -> BasePrimitive>()
