package org.rerenderer.android

import org.rerenderer.android.primitives.BasePrimitive

class Event<T> {
    val handlers = mutableListOf<(T) -> Unit>()

    fun on(handler: (T) -> Unit) {
        if (handler !in handlers) {
            handlers.plusAssign(handler)
        }
    }

    operator fun invoke(arg: T) {
        for (subscriber in handlers) subscriber(arg)
    }
}

object events {
    data class RenderRequest(val tree: BasePrimitive, val scale: Boolean)

    val render = Event<RenderRequest>()

    data class PlatformEvent(val name: String, val data: Map<String, Any>)

    val platformEvent = Event<PlatformEvent>()

    data class PlatformInformation(val width: Int, val height: Int)

    val updatePlatformInformation = Event<PlatformInformation>()

    operator fun invoke(init: events.() -> Unit) = init()
}