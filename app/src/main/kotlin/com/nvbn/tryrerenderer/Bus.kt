package com.nvbn.tryrerenderer

import org.jetbrains.anko.async
import java.util.concurrent.Executors

class Bus(
        val onInterpreter: (request: InterpreteRequest) -> Unit,
        val execute: (js: String) -> Unit
) {
    data class InterpreteRequest(val script: List<Instruction>,
                                 val root: Var.Ref,
                                 val scale: Boolean)

    data class Event(val name: String, val data: Map<String, Any>)

    val readExecutor = Executors.newFixedThreadPool(4)

    fun interprete(data: String) = async(readExecutor) {
        val request = parser.decode<InterpreteRequest>(data)
        onInterpreter(request)
    }

    fun sendEvent(event: Event) {
        val serialized = parser.encode(event)
        execute("androidEventsCallback('$serialized');")
    }

    fun updateInformation(width: Int, height: Int) {
        execute("androidUpdateInformation($width, $height);")
    }
}
