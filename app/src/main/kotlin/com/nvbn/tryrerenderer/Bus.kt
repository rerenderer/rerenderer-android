package com.nvbn.tryrerenderer

class Bus(
        val onInterpreter: (request: InterpreteRequest) -> Unit,
        val execute: (js: String) -> Unit
) {
    data class InterpreteRequest(val script: List<Instruction>, val root: String, val scale: Boolean)

    data class Event(val name: String, val data: Map<String, Any>)

    fun interprete(data: String) {
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
