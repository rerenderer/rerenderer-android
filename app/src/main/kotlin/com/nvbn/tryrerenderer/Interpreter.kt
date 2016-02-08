package com.nvbn.tryrerenderer

import kotlin.reflect.KClass

/**
 * Accessor to variable in pool.
 */
//abstract class _Variable {
//    abstract fun get(pool: Map<String, Any?>): Any?
//
//
//    class Reference(private val ref: String) : Var() {
//        override fun extractVar(pool: Map<String, Any?>) = pool[ref]
//    }
//
//    class Value(private val value: Any?) : Var() {
//        override fun extractVar(pool: Map<String, Any?>) = value
//    }
//}

/**
 * Rerenderer script command.
 */
abstract class Command {
    abstract fun interprete(pool: Map<String, Any?>): Map<String, Any?>

    fun List<Var>.getAll(pool: Map<String, Any?>) = map { it.extractVar(pool)!! }

    class NotClassException(notCls: Any?) : Exception("Isn't class $notCls!")

    class EmptyVariableException(variable: Var) : Exception("Ref $variable is empty!")

    class New(private val resultRef: String, private val cls: Var,
              private val args: List<Var>) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val cls = cls.extractVar(pool)
            if (cls !is KClass<*>) throw NotClassException(cls)
            return pool.plus(resultRef to cls.rNew(args.map { it.extractVar(pool)!! }))
        }
    }

    class Call(private val resultRef: String, private val obj: Var,
               private val method: String, private val args: List<Var>) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.extractVar(pool)
            return pool.plus(resultRef to when (obj) {
                is KClass<*> -> obj.rCall(method, args.getAll(pool))
                is Any -> obj.rCall(method, args.getAll(pool))
                else -> throw EmptyVariableException(this.obj)
            })
        }
    }

    class Get(private val resultRef: String, private val obj: Var,
              private val attr: String) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.extractVar(pool)
            if (obj !is KClass<*>) throw NotClassException(obj)
            return pool.plus(resultRef to obj.rGet(attr))
        }
    }

    class Free(private val ref: String) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> = pool.plus(
                ref to null)
    }

    companion object {
//        private fun translateArgs(args: Any): List<Variable> =
//                (args as List<List<Any>>).map { Variable.fromSource(it) }
//
//        fun fromSource(line: List<Any>) = when (line[0]) {
//            ":new" -> Command.New(
//                    line[1] as String,
//                    Variable.Reference(line[2] as String),
//                    translateArgs(line[3]))
//            ":call" -> Command.Call(
//                    line[1] as String,
//                    Variable.Reference(line[2] as String),
//                    line[3] as String,
//                    translateArgs(line[4]))
//            ":get" -> Command.Get(
//                    line[1] as String,
//                    Variable.Reference(line[2] as String),
//                    line[3] as String)
//            ":free" -> Command.Free(line[1] as String)
//            else -> throw Exception("Illegal instruction $line")
//        }
    }
}

class Interpreter {
    var pool = initialPool

    fun execute(script: List<List<Any>>) {
//        pool = script.map { Command.fromSource(it) }
//                .fold(pool) { pool, command -> command.interprete(pool) }
    }
}
