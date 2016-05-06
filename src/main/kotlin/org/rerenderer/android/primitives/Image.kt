package org.rerenderer.android.primitives

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import java.net.URL

val imagesCache = mutableMapOf<String, Bitmap>()

class Image(props: Map<String, Any?>,
            childs: List<BasePrimitive>,
            path: String,
            val ctx: Context) : BasePrimitive(props, childs, path) {

    fun clippedWidth(img: Bitmap) = if (prop<Int>("sx") + prop<Int>("width") > img.width) {
        img.width - prop<Int>("sx")
    } else {
        prop<Int>("width")
    }

    fun clippedHeight(img: Bitmap) = if (prop<Int>("sy") + prop<Int>("height") > img.height) {
        img.height - prop<Int>("sy")
    } else {
        prop<Int>("height")
    }

    override fun render(canvas: Canvas, paint: Paint) {
        val bitmap = bitmapFromUrl(prop<String>("src"))

        val clipped = Bitmap.createBitmap(bitmap,
                prop("sx"), prop("sy"),
                clippedWidth(bitmap), clippedHeight(bitmap))
        canvas.drawBitmap(
                clipped, 0f, 0f, paint)
    }

    fun loadBitmap(url: String): Bitmap {
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

    fun bitmapFromUrl(url: String): Bitmap {
        if (imagesCache[url] == null) {
            imagesCache[url] = loadBitmap(url)
        }

        return imagesCache[url]!!
    }

    companion object {
        fun register(ctx: Context) {
            registry["bundled.image"] = {props, childs, path ->
                Image(props, childs, path, ctx)
            }
        }
    }
}