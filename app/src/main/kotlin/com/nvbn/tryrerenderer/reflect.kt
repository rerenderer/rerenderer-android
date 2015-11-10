package com.nvbn.tryrerenderer

import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

fun isNumericType(type: KType) = when (type) {
    Short::class.defaultType -> true
    Int::class.defaultType -> true
    Long::class.defaultType -> true
    Float::class.defaultType -> true
    Double::class.defaultType -> true
    else -> false
}

fun isCastable(param: KParameter, inst: Any?): Boolean = when {
    (inst!!.javaClass.kotlin.defaultType.javaType == param.type.javaType) -> true
    (isNumericType(param.type) && inst is Number) -> true
    else -> false
}

fun isSameSignature(params: List<KParameter>, args: List<Any?>): Boolean {
    if (params.count() != args.count())
        return false

    for (pair in params.zip(args))
        if (!isCastable(pair.first, pair.second))
            return false

    return true
}

fun prepareNumericArg(type: KType, arg: Number) = when (type) {
    Short::class.defaultType -> arg.toShort()
    Int::class.defaultType -> arg.toInt()
    Long::class.defaultType -> arg.toLong()
    Float::class.defaultType -> arg.toFloat()
    Double::class.defaultType -> arg.toDouble()
    else -> throw Exception("Non-castable!")
}

fun prepareArgs(params: List<KParameter>, args: List<Any?>): Array<Any?> =
    params.zip(args).map({ pair: Pair<KParameter, Any?> ->
        val arg = pair.second
        if (arg is Number)
            prepareNumericArg(pair.first.type, arg)
        else
            pair.second
    }).toTypedArray()

fun new(cls: String, args: List<Any?>): Any? {
    val klass = Class.forName("android.graphics.$cls").kotlin
    val constructor = klass.constructors.filter {
        isSameSignature(it.parameters, args)
    }[0]!!
    return constructor.call(*prepareArgs(constructor.parameters, args))
}
//
//fun callStatic(cls: String, method: String, args: List<Any?>): Any? = {}
//
//fun callMember(obj: Any?, method: String, args: List<Any?>): Any? = {}
//
//fun call(obj: Any?, method: String, args: List<Any?>): Any? = when (obj) {
//    is String -> callStatic(obj, method, args)
//    else -> callMember(obj, method, args)
//}
//
//// Only static supported
//fun get(cls: String, attr: String): Any? = {}
