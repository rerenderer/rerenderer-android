package com.nvbn.tryrerenderer

import kotlin.reflect.KClass

sealed class Var {
    abstract fun extractVar(pool: Map<String, Any?>): Any?

    class Ref(val id: String) : Var() {
        override fun extractVar(pool: Map<String, Any?>) = pool[id]
    }

    class Val(val value: Any?) : Var() {
        override fun extractVar(pool: Map<String, Any?>) = value
    }

    class Static(val id: String) : Var() {
        override fun extractVar(pool: Map<String, Any?>) = reflection.Static(id)
    }
}

sealed class Instruction {
    abstract fun interprete(pool: Map<String, Any?>): Map<String, Any?>

    fun extractVars(vars: List<Var>, pool: Map<String, Any?>) = vars.map {
        it.extractVar(pool)!!
    }

    class NotClassException(notCls: Any?) : Exception("Isn't class $notCls!")

    class EmptyVariableException(variable: Var) : Exception("Ref $variable is empty!")

    class New(val resultRef: Var.Ref, val cls: Var,
              val args: List<Var>) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val cls = cls.extractVar(pool)
            if (cls !is KClass<*>)
                throw NotClassException(cls)

            val inst = reflection.new(cls, extractVars(args, pool))
            return pool.plus(resultRef.id to inst)
        }
    }

    class Call(val resultRef: Var.Ref, val obj: Var,
               val method: String, val args: List<Var>) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.extractVar(pool)
            val args = extractVars(args, pool)

            val result = when (obj) {
                is KClass<*> -> reflection.call(obj, method, args)
                is Any -> reflection.call(obj, method, args)
                else -> throw EmptyVariableException(this.obj)
            }

            return pool.plus(resultRef.id to result)
        }
    }

    class Get(val resultRef: Var.Ref, val obj: Var,
              val attr: String) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.extractVar(pool)

            val result = when (obj) {
                is KClass<*> -> reflection.get(obj, attr)
                is reflection.Static -> reflection.get(obj, attr)
                is Any -> reflection.get(obj, attr)
                else -> throw EmptyVariableException(this.obj)
            }

            return pool.plus(resultRef.id to result)
        }
    }

    class Free(val ref: Var.Ref) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> =
                pool.plus(ref.id to null)
    }
}
