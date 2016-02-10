package com.nvbn.tryrerenderer

class Interpreter {
    var pool = mapOf<String, Any?>()

    fun interprete(script: List<Instruction>): Map<String, Any?>  {
        pool = script.fold(pool) { pool, instruction ->
            instruction.interprete(pool)
        }
        return pool
    }
}
