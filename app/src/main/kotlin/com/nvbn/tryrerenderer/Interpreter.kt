package com.nvbn.tryrerenderer

import org.jetbrains.anko.AnkoLogger

class Interpreter: AnkoLogger {
    var pool = mapOf<String, Any?>()

    fun interprete(script: List<Instruction>): Map<String, Any?>  {
        pool = script.fold(pool) { pool, instruction ->
            instruction.interprete(pool)
        }
        return pool
    }
}
