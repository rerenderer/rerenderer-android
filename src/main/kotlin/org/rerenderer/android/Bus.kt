package org.rerenderer.android

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.async
import org.jetbrains.anko.info
import org.jetbrains.anko.warn
import org.rerenderer.android.primitives.BasePrimitive
import java.util.concurrent.Executors

class Bus(
        val renderFn: (BasePrimitive, Boolean) -> Unit,
        val execute: (js: String) -> Unit
): AnkoLogger {
    data class Event(val name: String, val data: Map<String, Any>)

    data class RenderRequest(val tree: BasePrimitive, val scale: Boolean)

    val readExecutor = Executors.newFixedThreadPool(2)

    fun render(data: String) = async(readExecutor) {
        val request = parser.decode<RenderRequest>(data)
        renderFn(request.tree, request.scale)
    }

    fun sendEvent(event: Event) {
        val serialized = parser.encode(event)
        execute("androidEventsCallback('$serialized');")
    }

    fun updateInformation(width: Int, height: Int) {
        try {
            execute("androidUpdateInformation($width, $height);")
        } catch (e: Exception) {
            warn("Can't update information: $e")
        }
    }
}
