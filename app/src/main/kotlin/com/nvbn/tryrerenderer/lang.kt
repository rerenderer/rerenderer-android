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
        // TODO: implement!
        override fun extractVar(pool: Map<String, Any?>) = null
    }
}

sealed class Instruction {
    abstract fun interprete(pool: Map<String, Any?>): Map<String, Any?>

    fun List<Var>.getAll(pool: Map<String, Any?>) = map { it.extractVar(pool)!! }

    class NotClassException(notCls: Any?) : Exception("Isn't class $notCls!")

    class EmptyVariableException(variable: Var) : Exception("Ref $variable is empty!")

    class New(val resultRef: Var.Ref, val cls: Var,
              val args: List<Var>) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val cls = cls.extractVar(pool)
            if (cls !is KClass<*>) throw NotClassException(cls)
            return pool.plus(resultRef.id to cls.rNew(args.map { it.extractVar(pool)!! }))
        }
    }

    class Call(val resultRef: Var.Ref, val obj: Var,
               val method: String, val args: List<Var>) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.extractVar(pool)
            return pool.plus(resultRef.id to when (obj) {
                is KClass<*> -> obj.rCall(method, args.getAll(pool))
                is Any -> obj.rCall(method, args.getAll(pool))
                else -> throw EmptyVariableException(this.obj)
            })
        }
    }

    class Get(val resultRef: Var.Ref, val obj: Var,
              val attr: String) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> {
            val obj = obj.extractVar(pool)
            if (obj !is KClass<*>) throw NotClassException(obj)
            return pool.plus(resultRef.id to obj.rGet(attr))
        }
    }

    class Free(val ref: Var.Ref) : Instruction() {
        override fun interprete(pool: Map<String, Any?>): Map<String, Any?> =
                pool.plus(ref.id to null)
    }
}
