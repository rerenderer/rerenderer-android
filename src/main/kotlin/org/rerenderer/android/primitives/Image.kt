package org.rerenderer.android.primitives

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import java.net.URL

class Image(props: Map<String, Any?>,
            childs: List<BasePrimitive>,
            path: String,
            val ctx: Context) : BasePrimitive(props, childs, path) {

    override fun render(canvas: Canvas, paint: Paint) {
        throw UnsupportedOperationException()
    }

    fun bitmapFromUrl(url: String): Bitmap {
        if (url.startsWith("file:///")) {
            val name = url.replace("file:///android_asset/", "")
            val asset = ctx.assets.open(name)
            return BitmapFactory.decodeStream(asset)
        } else {
            val connection = URL(url).openConnection()
            connection.connect()
            return BitmapFactory.decodeStream(connection.inputStream)
        }
    }

    companion object {
        fun register(ctx: Context) {
            registry["bundled.image"] = {props, childs, path ->
                Image(props, childs, path, ctx)
            }
        }
    }
}