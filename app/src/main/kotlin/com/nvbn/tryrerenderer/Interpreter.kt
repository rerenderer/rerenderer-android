package com.nvbn.tryrerenderer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.util.Log
import com.cognitect.transit.Keyword
import com.cognitect.transit.Symbol

// Vars
open class VarOrVal()

class Var(val id: String) : VarOrVal() {
    fun toVal(vars: Map<String, Any?>) = Val(vars.get(id))
}

class Val(val value: Any?) : VarOrVal()

fun varToVal(vars: Map<String, Any?>, x: VarOrVal): Any? = when (x) {
    is Var -> Val(vars.get(x.id))
    is Val -> x
    else -> throw Exception("Can't unpack $x")
}

fun toVarsOrVals(xs: Any): List<VarOrVal> = (xs as List<List<*>>).map { x ->
    when (x.get(0)) {
        ":var" -> Var(x.get(1) as String)
        ":val" -> Val(x.get(1))
        else -> throw Exception("Unknown variable definition $x")
    }
}

open class Command()

class New(val resultVar: String, val cls: String, val args: List<VarOrVal>) : Command()

class Call(val resultVar: String, val objVar: String, val method: String,
           val args: List<VarOrVal>) : Command()

class Get(val resultVar: String, val objVar: String, val attr: String) : Command()

class Free(val objVar: String) : Command()


class Stopped : Exception()

class Interpreter {
    val TAG = "INTERPRETE"
    var callId = 0
    var vars = mapOf<String, Any?>()

    fun call(script: Collection<List<Any>>, rootId: String): Bitmap {
        callId += 1
        val currentCallId = callId

        val actions = script.map {
            when (it[0]) {
                ":new" -> New(
                        it[1] as String,
                        it[2] as String,
                        toVarsOrVals(it[3]))
                ":call" -> Call(
                        it[1] as String,
                        it[2] as String,
                        it[3] as String,
                        toVarsOrVals(it[4]))
                ":get" -> Get(
                        it[1] as String,
                        it[2] as String,
                        it[3] as String)
                ":free" -> Free(it[1] as String)
                else -> throw Exception("Unknown action $it")
            }
        }
        vars = actions.fold(vars, { vars, action ->
            if (callId != currentCallId)
                throw Stopped()
            interpeteLine(vars, action)
        })
        val root = vars[rootId]
        return when (root) {
            is Bitmap -> root
            else -> throw Exception("Root should be instance of Bitmap, not $root")
        }
    }

    fun prepareArg(vars: Map<String, Any?>, arg: VarOrVal): Any? = when (arg) {
        is Var -> arg.toVal(vars).value
        is Val -> arg.value
        else -> throw Exception()
    }

    fun prepareArgs(vars: Map<String, Any?>, args: List<VarOrVal>): List<Any?> = args.map { arg ->
        prepareArg(vars, arg)
    }

    fun interpeteLine(vars: Map<String, Any?>, line: Command): Map<String, Any?> = when (line) {
        is New -> vars.plus(line.resultVar to doNew(
                vars, line.cls, prepareArgs(vars, line.args)))
        is Call -> vars.plus(line.resultVar to doCall(
                vars, vars.getOrElse(line.objVar, { -> line.objVar }),
                line.method, prepareArgs(vars, line.args)))
        is Get -> vars.plus(line.resultVar to doGet(
                vars, line.objVar, line.attr))
        is Free -> vars.minus(line.objVar)
        else -> vars
    }

}
