package org.rerenderer.android

import org.jetbrains.anko.AnkoLogger

class Interpreter: AnkoLogger {
    var pool = mapOf<String, Any?>()

    fun interpret(script: List<Instruction>): Map<String, Any?>  {
        pool = script.fold(pool) { pool, instruction ->
            instruction.interpret(pool)
        }
        return pool
    }
}
