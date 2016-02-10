package com.nvbn.tryrerenderer

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.Gson
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.registerTypeAdapter
import com.github.salomonbrys.kotson.fromJson


object reader {
    class NotAllowedVarException(variable: Any) : Exception(
            "Not allowed variable $variable")

    class NotAllowedInstructionException(instruction: Any) : Exception(
            "Not allowed instruction $instruction")

    val gson: Gson = makeParser()

    fun makeParser() = GsonBuilder()
            .registerTypeAdapter<Instruction> {
                deserialize {
                    when (it.json.array[0].asString) {
                        "new" -> Instruction.New(
                                gson.fromJson<Var>(it.json.array[1]) as Var.Ref,
                                gson.fromJson<Var>(it.json.array[2]),
                                gson.fromJson<List<Var>>(it.json.array[3]))
                        "call" -> Instruction.Call(
                                gson.fromJson<Var>(it.json.array[1]) as Var.Ref,
                                gson.fromJson<Var>(it.json.array[2]),
                                it.json.array[3].asString,
                                gson.fromJson<List<Var>>(it.json.array[4]))
                        "get" -> Instruction.Get(
                                gson.fromJson<Var>(it.json.array[1]) as Var.Ref,
                                gson.fromJson<Var>(it.json.array[2]),
                                it.json.array[3].asString)
                        "free" -> Instruction.Free(
                                gson.fromJson<Var>(it.json.array[1]) as Var.Ref)
                        else -> throw NotAllowedInstructionException(it)
                    }
                }
            }
            .registerTypeAdapter<List<Instruction>> {
                deserialize { it.json.array.map { gson.fromJson<Instruction>(it) } }
            }
            .registerTypeAdapter<Var> {
                deserialize {
                    when (it.json.array[0].asString) {
                        "ref" -> Var.Ref(it.json.array[1].asString)
                        "val" -> Var.Val(it.json.array[1].asAny)
                        "static" -> Var.Static(it.json.array[1].asString)
                        else -> throw NotAllowedVarException(it)
                    }
                }
            }
            .registerTypeAdapter<List<Var>> {
                deserialize { it.json.array.map { gson.fromJson<Var>(it) } }
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

    fun read(code: String) = gson.fromJson<List<Instruction>>(code)
}