package org.rerenderer.android

import com.github.salomonbrys.kotson.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.Gson
import org.jetbrains.anko.AnkoLogger
import org.rerenderer.android.primitives.BasePrimitive
import org.rerenderer.android.primitives.registry

object parser : AnkoLogger {
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
            .registerTypeAdapter<BasePrimitive> {
                deserialize {
                    val constructor = registry[it.json.array[0].asString]
                    constructor!!(
                            it.json.array[1].asMap,
                            gson.fromJson(it.json.array[2]),
                            it.json.array[3].asString)
                }
            }
            .registerTypeAdapter<List<BasePrimitive>> {
                deserialize {
                    it.json.array.map { gson.fromJson<BasePrimitive>(it) }
                }
            }
            .registerTypeAdapter<events.PlatformEvent> {
                serialize {
                    val obj = jsonObject("event" to it.src.name)
                    for ((key, value) in it.src.data) {
                        obj.add(key, toJsonElement(value))
                    }
                    obj
                }
            }
            .registerTypeAdapter<events.RenderRequest> {
                deserialize {
                    val tree = it.json.obj.get("tree")
                    val scale = it.json.obj.get("scale").asBoolean
                    events.RenderRequest(gson.fromJson(tree), scale)
                }
            }
            .create()

    val JsonElement.asPrimitive: Any? get() {
        val primitive = asJsonPrimitive
        return when {
            primitive.isString -> primitive.asString
        // All js side numbers is double
            primitive.isNumber -> primitive.asDouble
            primitive.isBoolean -> primitive.asBoolean
            else -> throw Exception()
        }
    }

    val JsonElement.asPrimitiveList: List<Any?> get() = asJsonArray.map {
        it.asPrimitive
    }

    val JsonElement.asMap: Map<String, Any?> get() = if (isJsonNull) {
        mapOf()
    } else {
        asJsonObject.toMap().mapValues {
            when {
                it.value.isJsonPrimitive -> it.value.asPrimitive
                it.value.isJsonArray -> it.value.asPrimitiveList
                else -> throw Exception()
            }
        }
    }

    inline fun <reified T : Any> decode(code: String): T = gson.fromJson<T>(code)

    inline fun <reified T : Any> encode(data: T): String = gson.typedToJson(data)
}
