package com.nvbn.tryrerenderer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

object RerendererLoader {
    fun bitmapFromUrl(url: String): Bitmap {
        val connection = URL(url).openConnection()
        connection.connect()
        val stream = connection.getInputStream()
        return BitmapFactory.decodeStream(stream)
    }
}
