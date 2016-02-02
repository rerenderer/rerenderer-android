package com.nvbn.tryrerenderer

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.Gson
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.simpleDeserialize
import com.github.salomonbrys.kotson.fromJson


class Reader {
    class NotAllowedVarException(variable: Any) : Exception(
            "Not allowed variable $variable")

    class NotAllowedInstructionException(instruction: Any) : Exception(
            "Not allowed instruction $instruction")

    val gson: Gson = makeParser()

    fun makeParser() = GsonBuilder()
            .simpleDeserialize<Instruction> {
                when (it.array[0].asString) {
                    "new" -> Instruction.New(
                            gson.fromJson<Var>(it.array[1]) as Var.Ref,
                            gson.fromJson<Var>(it.array[2]),
                            gson.fromJson<List<Var>>(it.array[3]))
                    "call" -> Instruction.Call(
                            gson.fromJson<Var>(it.array[1]) as Var.Ref,
                            gson.fromJson<Var>(it.array[2]),
                            it.array[3].asString,
                            gson.fromJson<List<Var>>(it.array[4]))
                    "get" -> Instruction.Get(
                            gson.fromJson<Var>(it.array[1]) as Var.Ref,
                            gson.fromJson<Var>(it.array[2]),
                            it.array[3].asString)
                    "free" -> Instruction.Free(
                            gson.fromJson<Var>(it.array[1]) as Var.Ref)
                    else -> throw NotAllowedInstructionException(it)
                }
            }
            .simpleDeserialize<Var> {
                when (it.array[0].asString) {
                    "ref" -> Var.Ref(it.array[1].asString)
                    "val" -> Var.Val(it.array[1].asAny)
                    "static" -> Var.Static(it.array[1].asString)
                    else -> throw NotAllowedVarException(it)
                }
            }
            .create()

    val JsonElement.asAny: Any get() {
        val primitive = asJsonPrimitive
        return when {
            primitive.isString -> primitive.asString
        // All js side numbers is double
            primitive.isNumber -> primitive.asDouble
            primitive.isBoolean -> primitive.asBoolean
            else -> throw Exception()
        }
    }

    fun read(code: String) = gson.fromJson<List<Command>>(code)
}