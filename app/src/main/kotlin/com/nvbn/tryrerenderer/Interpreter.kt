package com.nvbn.tryrerenderer

import android.graphics.Bitmap
import kotlin.reflect.KClass

/**
 * Accessor to variable in pool.
 */
abstract class Variable {
    abstract fun get(pool: Map<String, Any?>): Any?

    class Reference(private val ref: String) : Variable() {
        override fun get(pool: Map<String, Any?>) = pool[ref]
    }

    class Value(private val value: Any?) : Variable() {
        override fun get(pool: Map<String, Any?>) = value
    }
}

/**
 * Rerenderer script command.
 */
abstract class Command {
    abstract fun interprete(pool: Map<String, Any?>): Map<String, Any?>

    fun List<Variable>.getAll(pool: Map<String, Any?>) = map { it.get(pool)!! }

    class New(private val resultRef: String, private val cls: Variable,
              private val args: List<Variable>) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val cls = cls.get(pool)
            if (cls !is KClass<*>) throw Exception("Can't create instance of $cls")
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
                else -> throw NullPointerException("Can't call empty ref")
            })
        }
    }

    class Get(private val resultRef: String, private val obj: Variable,
              private val attr: String) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.get(pool)
            if (obj !is KClass<*>) throw Exception("Can't get $attr of $obj")

            return pool.plus(resultRef to obj.rGet(attr))
        }
    }

    class Free(private val ref: String) : Command() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> = pool.minus(ref)
    }
}

/**
 * Translate string to interpreter data structures.
 */
object Translator {
    fun translateArg(arg: List<Any>) = when (arg[0]) {
        ":val" -> Variable.Value(arg[1])
        ":var" -> Variable.Reference(arg[1] as String)
        else -> throw Exception("Not allowed arg $arg")
    }

    fun translateArgs(args: Any): List<Variable> = (args as List<List<Any>>).map { translateArg(it) }

    fun translate(script: List<List<Any>>): List<Command> = script.map { line ->
        when (line[0]) {
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

    fun interprete(script: List<List<Any>>, rootRef: String): Bitmap {
        pool = Translator.translate(script).fold(pool, { pool, command ->
            command.interprete(pool)
        })
        return pool[rootRef] as Bitmap
    }
}
