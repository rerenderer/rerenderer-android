package org.rerenderer.android

import org.jetbrains.anko.async
import java.util.concurrent.Executors

class Bus(
        val onInterpret: (request: InterpretRequest) -> Unit,
        val execute: (js: String) -> Unit
) {
    data class InterpretRequest(val script: List<Instruction>,
                                 val root: Var.Ref,
                                 val scale: Boolean)

    data class Event(val name: String, val data: Map<String, Any>)

    val readExecutor = Executors.newFixedThreadPool(4)

    fun interpret(data: String) = async(readExecutor) {
        val request = parser.decode<InterpretRequest>(data)
        onInterpret(request)
    }

    fun sendEvent(event: Event) {
        val serialized = parser.encode(event)
        execute("androidEventsCallback('$serialized');")
    }

    fun updateInformation(width: Int, height: Int) {
        execute("androidUpdateInformation($width, $height);")
    }
}
