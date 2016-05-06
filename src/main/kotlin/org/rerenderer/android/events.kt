package org.rerenderer.android

import org.rerenderer.android.primitives.BasePrimitive

open class Event<T> {
    var handlers = listOf<(T) -> Unit>()

    infix fun on(handler: (T) -> Unit) {
        handlers += handler
    }

    fun emit(event: T) {
        for (subscriber in handlers) {
            subscriber(event)
        }
    }
}

object events {
    data class RenderRequest(val tree: BasePrimitive, val scale: Boolean) {
        companion object : Event<RenderRequest>()

        fun emit() = Companion.emit(this)
    }

    data class PlatformEvent(val name: String, val data: Map<String, Any>) {
        companion object : Event<PlatformEvent>()

        fun emit() = Companion.emit(this)
    }

    data class PlatformInformation(val width: Int, val height: Int) {
        companion object : Event<PlatformInformation>()

        fun emit() = Companion.emit(this)
    }
}