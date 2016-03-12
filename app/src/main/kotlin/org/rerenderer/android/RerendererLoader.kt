package org.rerenderer.android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.net.URL

object RerendererLoader {
    val TAG = "RERENDERER_LOADER"
    var context: Context? = null

    fun bitmapFromUrl(url: String): Bitmap {
        Log.d(TAG, "Load asset $url")

        if (url.startsWith("file:///")) {
            val name = url.replace("file:///android_asset/", "")
            val asset = context!!.assets.open(name)
            return BitmapFactory.decodeStream(asset)
        } else {
            val connection = URL(url).openConnection()
            connection.connect()
            return BitmapFactory.decodeStream(connection.inputStream)
        }
    }
}
