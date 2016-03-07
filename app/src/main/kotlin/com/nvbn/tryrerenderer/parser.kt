package com.nvbn.tryrerenderer

import com.github.salomonbrys.kotson.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.Gson
import org.jetbrains.anko.AnkoLogger


object parser: AnkoLogger {
    class NotAllowedVarException(variable: Any) : Exception(
            "Not allowed variable $variable")

    class NotAllowedInstructionException(instruction: Any) : Exception(
            "Not allowed instruction $instruction")

    val gson: Gson = makeParser()

    fun toJsonElement(x: Any?): JsonElement {
        if (x == null)
            return jsonNull

        return when (x) {
            is Number -> x.toJson()
            is Char -> x.toJson()
            is Boolean -> x.toJson()
            is String -> x.toJson()
            is JsonElement -> x
            else -> throw IllegalArgumentException("${this} cannot be converted to JSON")
        }
    }

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
            .registerTypeAdapter<Bus.InterpreteRequest> {
                deserialize {
                    Bus.InterpreteRequest(
                            gson.fromJson<List<Instruction>>(it.json["script"]),
                            gson.fromJson<Var>(it.json["root"]) as Var.Ref,
                            it.json["scale"].bool)
                }
            }
            .registerTypeAdapter<Bus.Event> {
                serialize {
                    val obj = jsonObject("event" to it.src.name)
                    for ((key, value) in it.src.data) {
                        obj.add(key, toJsonElement(value))
                    }
                    obj
                }
            }
            .create()

    val JsonElement.asAny: Any? get() {
        if (isJsonNull)
            return null

        val primitive = asJsonPrimitive
        return when {
            primitive.isString -> primitive.asString
        // All js side numbers is double
            primitive.isNumber -> primitive.asDouble
            primitive.isBoolean -> primitive.asBoolean
            else -> throw Exception()
        }
    }

    inline fun <reified T : Any> decode(code: String): T = gson.fromJson<T>(code)

    inline fun <reified T: Any> encode(data: T): String = gson.typedToJson(data)
}
