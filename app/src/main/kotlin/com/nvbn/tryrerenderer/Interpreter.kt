package com.nvbn.tryrerenderer

import kotlin.reflect.KClass

/**
 * Accessor to variable in pool.
 */
abstract class Variable {
    abstract fun get(pool: Map<String, Any?>): Any?

    class NotAllowedVariableException(variable: List<Any>) : Exception("Not allowed variable $variable")

    class Reference(private val ref: String) : Variable() {
        override fun get(pool: Map<String, Any?>) = pool[ref]
    }

    class Value(private val value: Any?) : Variable() {
        override fun get(pool: Map<String, Any?>) = value
    }

    companion object {
        fun fromSource(variable: List<Any>) = when (variable[0]) {
            ":val" -> Variable.Value(variable[1])
            ":var" -> Variable.Reference(variable[1] as String)
            else -> throw NotAllowedVariableException(variable)
        }
    }
}

/**
 * Rerenderer script command.
 */
abstract class Command {
    abstract fun interprete(pool: Map<String, Any?>): Map<String, Any?>

    fun List<Variable>.getAll(pool: Map<String, Any?>) = map { it.get(pool)!! }

    class NotClassException(notCls: Any?) : Exception("Isn't class $notCls!")

    class EmptyVariableException(variable: Variable) : Exception("Ref $variable is empty!")

    class New(private val resultRef: String, private val cls: Variable,
              private val args: List<Variable>) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val cls = cls.get(pool)
            if (cls !is KClass<*>) throw NotClassException(cls)
            return pool.plus(resultRef to cls.rNew(args.map { it.get(pool)!! }))
        }
    }

    class Call(private val resultRef: String, private val obj: Variable,
               private val method: String, private val args: List<Variable>) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.get(pool)
            return pool.plus(resultRef to when (obj) {
                is KClass<*> -> obj.rCall(method, args.getAll(pool))
                is Any -> obj.rCall(method, args.getAll(pool))
                else -> throw EmptyVariableException(this.obj)
            })
        }
    }

    class Get(private val resultRef: String, private val obj: Variable,
              private val attr: String) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.get(pool)
            if (obj !is KClass<*>) throw NotClassException(obj)
            return pool.plus(resultRef to obj.rGet(attr))
        }
    }

    class Free(private val ref: String) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> = pool.minus(ref)
    }

    companion object {
        private fun translateArgs(args: Any): List<Variable> =
                (args as List<List<Any>>).map { Variable.fromSource(it) }

        fun fromSource(line: List<Any>) = when (line[0]) {
            ":new" -> Command.New(
                    line[1] as String,
                    Variable.Reference(line[2] as String),
                    translateArgs(line[3]))
            ":call" -> Command.Call(
                    line[1] as String,
                    Variable.Reference(line[2] as String),
                    line[3] as String,
                    translateArgs(line[4]))
            ":get" -> Command.Get(
                    line[1] as String,
                    Variable.Reference(line[2] as String),
                    line[3] as String)
            ":free" -> Command.Free(line[1] as String)
            else -> throw Exception("Illegal instruction $line")
        }
    }
}

class Interpreter {
    var pool = initialPool

    fun execute(script: List<List<Any>>) {
        pool = script.map { Command.fromSource(it) }
                .fold(pool) { pool, command -> command.interprete(pool) }
    }
}
