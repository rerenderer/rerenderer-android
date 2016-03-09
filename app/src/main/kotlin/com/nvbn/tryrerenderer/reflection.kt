package com.nvbn.tryrerenderer

import kotlin.reflect.*
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.javaMethod

object reflection {
    data class Static(val path: String)

    private fun argTypes(args: List<Any>) = args.map {
        it.javaClass.kotlin.qualifiedName
    }.joinToString(", ")

    /**
     * Returns true when cls is numeric.
     */
    private fun isNumeric(cls: KClass<*>) = listOf(
            Number::class.qualifiedName,
            Double::class.qualifiedName,
            Float::class.qualifiedName,
            Long::class.qualifiedName,
            Int::class.qualifiedName,
            Short::class.qualifiedName,
            Byte::class.qualifiedName).contains(cls.qualifiedName)

    /**
     * Returns true when arg compatible with cls.
     */
    private fun compatible(param: KClass<*>, arg: Any) = when {
        param == arg.javaClass.kotlin -> true
        isNumeric(param) && arg is Number -> true
        else -> false
    }

    /**
     * Returns true when list of args compatible with list of parameter types.
     */
    private fun compatible(parameterTypes: Array<Class<*>>, args: List<Any>) = when {
        parameterTypes.count() != args.count() -> false
        parameterTypes.zip(args).all {
            val (param, arg) = it
            compatible(param.kotlin, arg)
        } -> true
        else -> false
    }

    /**
     * Casts number to numeric cls.
     */
    private fun castNumeric(arg: Number, cls: KClass<*>) = when (cls.qualifiedName) {
        Double::class.qualifiedName -> arg.toDouble()
        Float::class.qualifiedName -> arg.toFloat()
        Long::class.qualifiedName -> arg.toLong()
        Int::class.qualifiedName -> arg.toInt()
        Short::class.qualifiedName -> arg.toShort()
        Byte::class.qualifiedName -> arg.toByte()
        else -> arg
    }

    /**
     * Prepares args for calling function.
     */
    private fun prepareArgs(args: List<Any>, parameterTypes: Array<Class<*>>) =
            args.zip(parameterTypes).map {
                val (arg, param) = it
                when {
                    isNumeric(param.kotlin) -> castNumeric(arg as Number, param.kotlin)
                    else -> arg
                }
            }

    val staticPathsCache = Cache<Any>()

    /**
     * Gets class by static and path.
     */
    fun get(obj: Static, attr: String): Any {
        val path = "${obj.path}.$attr"
        return staticPathsCache.get(path) {
            try {
                Class.forName(path).kotlin
            } catch (e: Exception) {
                Static(path)
            }
        }
    }

    val staticAttributesCache = Cache<Any?>()

    /**
     * Gets static attribute of class.
     */
    fun get(obj: KClass<*>, attr: String): Any? =
            staticAttributesCache.get("${obj.qualifiedName}::$attr") {
                if (obj.java.isEnum) {
                    obj.java.enumConstants.filter {
                        it.toString() == attr
                    }[0]
                } else {
                    val prop = obj.staticProperties.filter({ it.name == attr })[0]
                    prop.get()
                }
            }

    val attributesCache = Cache<KProperty1<Any, Any?>>()

    /**
     * Gets attribute of instance.
     */
    fun get(obj: Any, attr: String): Any? {
        val cls = obj.javaClass.kotlin
        val prop = attributesCache.get("${cls.qualifiedName}.$attr") {
            cls.declaredMemberProperties.filter({ it.name == attr })[0]
        }
        return prop.get(obj)
    }

    val staticMethodsCache = Cache<KFunction<Any?>>()

    /**
     * Calls static method of class.
     */
    fun call(obj: KClass<*>, method: String, args: List<Any>): Any? {
        val fn = staticMethodsCache.get("${obj.qualifiedName}::$method(${argTypes(args)})") {
            obj.staticFunctions.filter({
                it.name == method && compatible(
                        it.javaMethod!!.parameterTypes, args)
            })[0]
        }
        return fn.call(*prepareArgs(
                args, fn.javaMethod!!.parameterTypes).toTypedArray())
    }

    val methodsCache = Cache<KFunction<Any?>>()

    /**
     * Class method of instance.
     */
    fun call(obj: Any, method: String, args: List<Any>): Any? {
        val cls = obj.javaClass.kotlin
        val fn = methodsCache.get("${cls.qualifiedName}.$method(${argTypes(args)})") {
            cls.declaredFunctions.filter({
                it.name == method && compatible(
                        it.javaMethod!!.parameterTypes, args)
            })[0]
        }
        val fnArgs = listOf(obj).plus(prepareArgs(
                args, fn.javaMethod!!.parameterTypes))
        return fn.call(*fnArgs.toTypedArray())
    }

    val constructorsCache = Cache<KFunction<Any>>()

    /**
     * Creates instance of class.
     */
    fun new(cls: KClass<*>, args: List<Any>): Any {
        val constructor = constructorsCache.get("${cls.qualifiedName}(${argTypes(args)})") {
            cls.constructors.filter({
                compatible(it.javaConstructor!!.parameterTypes, args)
            })[0]
        }
        return constructor.call(*prepareArgs(
                args, constructor.javaConstructor!!.parameterTypes).toTypedArray())
    }
}
