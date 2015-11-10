package com.nvbn.tryrerenderer

import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Bitmap
import android.graphics.PorterDuffColorFilter
import android.graphics.SweepGradient
import android.graphics.ColorMatrixColorFilter
import android.graphics.Color
import android.graphics.Rasterizer
import android.graphics.LinearGradient
import android.graphics.BitmapShader
import android.util.DisplayMetrics
import android.graphics.EmbossMaskFilter
import android.graphics.Picture
import android.graphics.PixelXorXfermode
import android.graphics.Xfermode
import android.graphics.Typeface
import android.graphics.PorterDuff
import android.graphics.ColorFilter
import android.graphics.Rect
import java.io.InputStream
import android.graphics.BlurMaskFilter
import android.graphics.RectF
import android.graphics.ComposePathEffect
import android.graphics.LayerRasterizer
import android.content.res.AssetManager
import android.graphics.Matrix
import java.io.File
import android.graphics.Path
import android.graphics.DrawFilter
import android.graphics.RadialGradient
import android.graphics.SumPathEffect
import android.os.Parcelable
import android.graphics.MaskFilter
import android.graphics.Region
import java.util.Locale
import android.graphics.AvoidXfermode
import java.io.Closeable
import android.text.TextPaint
import android.os.Parcel
import android.graphics.DiscretePathEffect
import android.graphics.PathDashPathEffect
import android.graphics.Shader
import android.graphics.Canvas
import java.io.OutputStream
import android.graphics.PathEffect
import java.nio.Buffer
import android.graphics.PorterDuffXfermode
import android.graphics.DashPathEffect
import android.graphics.LightingColorFilter
import android.graphics.ComposeShader
import android.graphics.PaintFlagsDrawFilter


fun anyToByte(x: Any?): Byte = when (x) {
    is Short -> x.toByte()
    is Int -> x.toByte()
    is Long -> x.toByte()
    is Float -> x.toByte()
    is Double -> x.toByte()
    else -> x as Byte
}

fun anyToShort(x: Any?): Short = when (x) {
    is Byte -> x.toShort()
    is Int -> x.toShort()
    is Long -> x.toShort()
    is Float -> x.toShort()
    is Double -> x.toShort()
    else -> x as Short
}

fun anyToInt(x: Any?): Int = when (x) {
    is Byte -> x.toInt()
    is Short -> x.toInt()
    is Long -> x.toInt()
    is Float -> x.toInt()
    is Double -> x.toInt()
    else -> x as Int
}

fun anyToLong(x: Any?): Long = when (x) {
    is Byte -> x.toLong()
    is Short -> x.toLong()
    is Int -> x.toLong()
    is Float -> x.toLong()
    is Double -> x.toLong()
    else -> x as Long
}

fun anyToFloat(x: Any?): Float = when (x) {
    is Byte -> x.toFloat()
    is Short -> x.toFloat()
    is Int -> x.toFloat()
    is Long -> x.toFloat()
    is Double -> x.toFloat()
    else -> x as Float
}

fun anyToDouble(x: Any?): Double = when (x) {
    is Byte -> x.toDouble()
    is Short -> x.toDouble()
    is Int -> x.toDouble()
    is Long -> x.toDouble()
    is Float -> x.toDouble()
    else -> x as Double
}

fun getNewMap(): Map<NewGroup, (args: List<Any?>) -> Any?> = mapOf(
        NewGroup("Xfermode", 0) to { args: List<Any?> ->
            Xfermode()
        },
        NewGroup("Shader", 0) to { args: List<Any?> ->
            Shader()
        },
        NewGroup("Picture", 1) to { args: List<Any?> ->
            Picture(args[0] as Picture)
        },
        NewGroup("Paint", 1) to { args: List<Any?> ->
            when {
                (true) -> Paint(anyToInt(args[0]))
                (true && args[0] is Paint) -> Paint(args[0] as Paint)
                else -> throw Exception("Can't create Paint wtih $args")
            }
        },
        NewGroup("MaskFilter", 0) to { args: List<Any?> ->
            MaskFilter()
        },
        NewGroup("Rect", 1) to { args: List<Any?> ->
            Rect(args[0] as Rect)
        },
        NewGroup("Canvas", 0) to { args: List<Any?> ->
            Canvas()
        },
        NewGroup("Paint\$FontMetrics", 0) to { args: List<Any?> ->
            Paint.FontMetrics()
        },
        NewGroup("Paint", 0) to { args: List<Any?> ->
            Paint()
        },
        NewGroup("Paint\$FontMetricsInt", 0) to { args: List<Any?> ->
            Paint.FontMetricsInt()
        },
        NewGroup("Color", 0) to { args: List<Any?> ->
            Color()
        },
        NewGroup("Path", 0) to { args: List<Any?> ->
            Path()
        },
        NewGroup("DrawFilter", 0) to { args: List<Any?> ->
            DrawFilter()
        },
        NewGroup("RectF", 4) to { args: List<Any?> ->
            RectF(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
        },
        NewGroup("RectF", 1) to { args: List<Any?> ->
            when {
                (true && args[0] is RectF) -> RectF(args[0] as RectF)
                (true && args[0] is Rect) -> RectF(args[0] as Rect)
                else -> throw Exception("Can't create RectF wtih $args")
            }
        },
        NewGroup("Rect", 0) to { args: List<Any?> ->
            Rect()
        },
        NewGroup("Rect", 4) to { args: List<Any?> ->
            Rect(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
        },
        NewGroup("Picture", 0) to { args: List<Any?> ->
            Picture()
        },
        NewGroup("Region", 0) to { args: List<Any?> ->
            Region()
        },
        NewGroup("Matrix", 0) to { args: List<Any?> ->
            Matrix()
        },
        NewGroup("ColorFilter", 0) to { args: List<Any?> ->
            ColorFilter()
        },
        NewGroup("Canvas", 1) to { args: List<Any?> ->
            Canvas(args[0] as Bitmap)
        },
        NewGroup("Rasterizer", 0) to { args: List<Any?> ->
            Rasterizer()
        },
        NewGroup("Path", 1) to { args: List<Any?> ->
            Path(args[0] as Path)
        },
        NewGroup("Region", 1) to { args: List<Any?> ->
            when {
                (true && args[0] is Region) -> Region(args[0] as Region)
                (true && args[0] is Rect) -> Region(args[0] as Rect)
                else -> throw Exception("Can't create Region wtih $args")
            }
        },
        NewGroup("Region", 4) to { args: List<Any?> ->
            Region(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
        },
        NewGroup("RectF", 0) to { args: List<Any?> ->
            RectF()
        },
        NewGroup("PathEffect", 0) to { args: List<Any?> ->
            PathEffect()
        },
        NewGroup("Matrix", 1) to { args: List<Any?> ->
            Matrix(args[0] as Matrix)
        }
)

fun doCall(vars: Map<String, Any?>, objVar: Any?, method: String, args: List<Any?>): Any = when {
    (objVar == "Canvas\$EdgeType" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Canvas.EdgeType.valueOf(args[0] as String)
    (objVar == "Canvas\$EdgeType" && method == "values" && args.count() == 0 ) -> Canvas.EdgeType.values()
    (objVar is Path && method == "addArc" && args.count() == 3 && args[0] is RectF) -> objVar.addArc(args[0] as RectF, anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Path && method == "addArc" && args.count() == 6 ) -> objVar.addArc(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]))
    (objVar is Path && method == "addCircle" && args.count() == 4 && args[3] is Path.Direction) -> objVar.addCircle(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Path.Direction)
    (objVar is Path && method == "addOval" && args.count() == 5 && args[4] is Path.Direction) -> objVar.addOval(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Path.Direction)
    (objVar is Path && method == "addOval" && args.count() == 2 && args[0] is RectF && args[1] is Path.Direction) -> objVar.addOval(args[0] as RectF, args[1] as Path.Direction)
    (objVar is Path && method == "addPath" && args.count() == 3 && args[0] is Path) -> objVar.addPath(args[0] as Path, anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Path && method == "addPath" && args.count() == 1 && args[0] is Path) -> objVar.addPath(args[0] as Path)
    (objVar is Path && method == "addPath" && args.count() == 2 && args[0] is Path && args[1] is Matrix) -> objVar.addPath(args[0] as Path, args[1] as Matrix)
    (objVar is Path && method == "addRect" && args.count() == 5 && args[4] is Path.Direction) -> objVar.addRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Path.Direction)
    (objVar is Path && method == "addRect" && args.count() == 2 && args[0] is RectF && args[1] is Path.Direction) -> objVar.addRect(args[0] as RectF, args[1] as Path.Direction)
    (objVar is Path && method == "addRoundRect" && args.count() == 7 && args[6] is Path.Direction) -> objVar.addRoundRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]), args[6] as Path.Direction)
    (objVar is Path && method == "addRoundRect" && args.count() == 6 && args[4] is FloatArray && args[5] is Path.Direction) -> objVar.addRoundRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as FloatArray, args[5] as Path.Direction)
    (objVar is Path && method == "addRoundRect" && args.count() == 3 && args[0] is RectF && args[1] is FloatArray && args[2] is Path.Direction) -> objVar.addRoundRect(args[0] as RectF, args[1] as FloatArray, args[2] as Path.Direction)
    (objVar is Path && method == "addRoundRect" && args.count() == 4 && args[0] is RectF && args[3] is Path.Direction) -> objVar.addRoundRect(args[0] as RectF, anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Path.Direction)
    (objVar is Path && method == "arcTo" && args.count() == 7 && args[6] is Boolean) -> objVar.arcTo(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]), args[6] as Boolean)
    (objVar is Path && method == "arcTo" && args.count() == 3 && args[0] is RectF) -> objVar.arcTo(args[0] as RectF, anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Path && method == "arcTo" && args.count() == 4 && args[0] is RectF && args[3] is Boolean) -> objVar.arcTo(args[0] as RectF, anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Boolean)
    (objVar is Path && method == "close" && args.count() == 0 ) -> objVar.close()
    (objVar is Path && method == "computeBounds" && args.count() == 2 && args[0] is RectF && args[1] is Boolean) -> objVar.computeBounds(args[0] as RectF, args[1] as Boolean)
    (objVar is Path && method == "cubicTo" && args.count() == 6 ) -> objVar.cubicTo(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]))
    (objVar is Path && method == "getFillType" && args.count() == 0 ) -> objVar.getFillType()
    (objVar is Path && method == "incReserve" && args.count() == 1 ) -> objVar.incReserve(anyToInt(args[0]))
    (objVar is Path && method == "isConvex" && args.count() == 0 ) -> objVar.isConvex()
    (objVar is Path && method == "isEmpty" && args.count() == 0 ) -> objVar.isEmpty()
    (objVar is Path && method == "isInverseFillType" && args.count() == 0 ) -> objVar.isInverseFillType()
    (objVar is Path && method == "isRect" && args.count() == 1 && args[0] is RectF) -> objVar.isRect(args[0] as RectF)
    (objVar is Path && method == "lineTo" && args.count() == 2 ) -> objVar.lineTo(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Path && method == "moveTo" && args.count() == 2 ) -> objVar.moveTo(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Path && method == "offset" && args.count() == 3 && args[2] is Path) -> objVar.offset(anyToFloat(args[0]), anyToFloat(args[1]), args[2] as Path)
    (objVar is Path && method == "offset" && args.count() == 2 ) -> objVar.offset(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Path && method == "op" && args.count() == 3 && args[0] is Path && args[1] is Path && args[2] is Path.Op) -> objVar.op(args[0] as Path, args[1] as Path, args[2] as Path.Op)
    (objVar is Path && method == "op" && args.count() == 2 && args[0] is Path && args[1] is Path.Op) -> objVar.op(args[0] as Path, args[1] as Path.Op)
    (objVar is Path && method == "quadTo" && args.count() == 4 ) -> objVar.quadTo(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Path && method == "rCubicTo" && args.count() == 6 ) -> objVar.rCubicTo(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]))
    (objVar is Path && method == "rLineTo" && args.count() == 2 ) -> objVar.rLineTo(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Path && method == "rMoveTo" && args.count() == 2 ) -> objVar.rMoveTo(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Path && method == "rQuadTo" && args.count() == 4 ) -> objVar.rQuadTo(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Path && method == "reset" && args.count() == 0 ) -> objVar.reset()
    (objVar is Path && method == "rewind" && args.count() == 0 ) -> objVar.rewind()
    (objVar is Path && method == "set" && args.count() == 1 && args[0] is Path) -> objVar.set(args[0] as Path)
    (objVar is Path && method == "setFillType" && args.count() == 1 && args[0] is Path.FillType) -> objVar.setFillType(args[0] as Path.FillType)
    (objVar is Path && method == "setLastPoint" && args.count() == 2 ) -> objVar.setLastPoint(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Path && method == "toggleInverseFillType" && args.count() == 0 ) -> objVar.toggleInverseFillType()
    (objVar is Path && method == "transform" && args.count() == 2 && args[0] is Matrix && args[1] is Path) -> objVar.transform(args[0] as Matrix, args[1] as Path)
    (objVar is Path && method == "transform" && args.count() == 1 && args[0] is Matrix) -> objVar.transform(args[0] as Matrix)
    (objVar is Picture && method == "beginRecording" && args.count() == 2 ) -> objVar.beginRecording(anyToInt(args[0]), anyToInt(args[1]))
    (objVar == "Picture" && method == "createFromStream" && args.count() == 1 && args[0] is InputStream) -> Picture.createFromStream(args[0] as InputStream)
    (objVar is Picture && method == "draw" && args.count() == 1 && args[0] is Canvas) -> objVar.draw(args[0] as Canvas)
    (objVar is Picture && method == "endRecording" && args.count() == 0 ) -> objVar.endRecording()
    (objVar is Picture && method == "getHeight" && args.count() == 0 ) -> objVar.getHeight()
    (objVar is Picture && method == "getWidth" && args.count() == 0 ) -> objVar.getWidth()
    (objVar is Picture && method == "writeToStream" && args.count() == 1 && args[0] is OutputStream) -> objVar.writeToStream(args[0] as OutputStream)
    (objVar == "Path\$Op" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Path.Op.valueOf(args[0] as String)
    (objVar == "Path\$Op" && method == "values" && args.count() == 0 ) -> Path.Op.values()
    (objVar == "Paint\$Style" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Paint.Style.valueOf(args[0] as String)
    (objVar == "Paint\$Style" && method == "values" && args.count() == 0 ) -> Paint.Style.values()
    (objVar == "Paint\$Align" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Paint.Align.valueOf(args[0] as String)
    (objVar == "Paint\$Align" && method == "values" && args.count() == 0 ) -> Paint.Align.values()
    (objVar == "Typeface" && method == "create" && args.count() == 2 && args[0] is String) -> Typeface.create(args[0] as String, anyToInt(args[1]))
    (objVar == "Typeface" && method == "create" && args.count() == 2 && args[0] is Typeface) -> Typeface.create(args[0] as Typeface, anyToInt(args[1]))
    (objVar == "Typeface" && method == "createFromAsset" && args.count() == 2 && args[0] is AssetManager && args[1] is String) -> Typeface.createFromAsset(args[0] as AssetManager, args[1] as String)
    (objVar == "Typeface" && method == "createFromFile" && args.count() == 1 && args[0] is String) -> Typeface.createFromFile(args[0] as String)
    (objVar == "Typeface" && method == "createFromFile" && args.count() == 1 && args[0] is File) -> Typeface.createFromFile(args[0] as File)
    (objVar == "Typeface" && method == "defaultFromStyle" && args.count() == 1 ) -> Typeface.defaultFromStyle(anyToInt(args[0]))
    (objVar is Typeface && method == "equals" && args.count() == 1 && args[0] is Any) -> objVar.equals(args[0] as Any)
    (objVar is Typeface && method == "getStyle" && args.count() == 0 ) -> objVar.getStyle()
    (objVar is Typeface && method == "hashCode" && args.count() == 0 ) -> objVar.hashCode()
    (objVar is Typeface && method == "isBold" && args.count() == 0 ) -> objVar.isBold()
    (objVar is Typeface && method == "isItalic" && args.count() == 0 ) -> objVar.isItalic()
    (objVar is Shader && method == "getLocalMatrix" && args.count() == 1 && args[0] is Matrix) -> objVar.getLocalMatrix(args[0] as Matrix)
    (objVar is Shader && method == "setLocalMatrix" && args.count() == 1 && args[0] is Matrix) -> objVar.setLocalMatrix(args[0] as Matrix)
    (objVar is Canvas && method == "clipPath" && args.count() == 1 && args[0] is Path) -> objVar.clipPath(args[0] as Path)
    (objVar is Canvas && method == "clipPath" && args.count() == 2 && args[0] is Path && args[1] is Region.Op) -> objVar.clipPath(args[0] as Path, args[1] as Region.Op)
    (objVar is Canvas && method == "clipRect" && args.count() == 2 && args[0] is Rect && args[1] is Region.Op) -> objVar.clipRect(args[0] as Rect, args[1] as Region.Op)
    (objVar is Canvas && method == "clipRect" && args.count() == 2 && args[0] is RectF && args[1] is Region.Op) -> objVar.clipRect(args[0] as RectF, args[1] as Region.Op)
    (objVar is Canvas && method == "clipRect" && args.count() == 4 ) -> objVar.clipRect(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Canvas && method == "clipRect" && args.count() == 4 ) -> objVar.clipRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Canvas && method == "clipRect" && args.count() == 1 && args[0] is RectF) -> objVar.clipRect(args[0] as RectF)
    (objVar is Canvas && method == "clipRect" && args.count() == 5 && args[4] is Region.Op) -> objVar.clipRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Region.Op)
    (objVar is Canvas && method == "clipRect" && args.count() == 1 && args[0] is Rect) -> objVar.clipRect(args[0] as Rect)
    (objVar is Canvas && method == "clipRegion" && args.count() == 1 && args[0] is Region) -> objVar.clipRegion(args[0] as Region)
    (objVar is Canvas && method == "clipRegion" && args.count() == 2 && args[0] is Region && args[1] is Region.Op) -> objVar.clipRegion(args[0] as Region, args[1] as Region.Op)
    (objVar is Canvas && method == "concat" && args.count() == 1 && args[0] is Matrix) -> objVar.concat(args[0] as Matrix)
    (objVar is Canvas && method == "drawARGB" && args.count() == 4 ) -> objVar.drawARGB(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Canvas && method == "drawArc" && args.count() == 5 && args[0] is RectF && args[3] is Boolean && args[4] is Paint) -> objVar.drawArc(args[0] as RectF, anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Boolean, args[4] as Paint)
    (objVar is Canvas && method == "drawArc" && args.count() == 8 && args[6] is Boolean && args[7] is Paint) -> objVar.drawArc(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]), args[6] as Boolean, args[7] as Paint)
    (objVar is Canvas && method == "drawBitmap" && args.count() == 9 && args[0] is IntArray && args[7] is Boolean && args[8] is Paint) -> objVar.drawBitmap(args[0] as IntArray, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToInt(args[5]), anyToInt(args[6]), args[7] as Boolean, args[8] as Paint)
    (objVar is Canvas && method == "drawBitmap" && args.count() == 3 && args[0] is Bitmap && args[1] is Matrix && args[2] is Paint) -> objVar.drawBitmap(args[0] as Bitmap, args[1] as Matrix, args[2] as Paint)
    (objVar is Canvas && method == "drawBitmap" && args.count() == 9 && args[0] is IntArray && args[7] is Boolean && args[8] is Paint) -> objVar.drawBitmap(args[0] as IntArray, anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]), anyToInt(args[5]), anyToInt(args[6]), args[7] as Boolean, args[8] as Paint)
    (objVar is Canvas && method == "drawBitmap" && args.count() == 4 && args[0] is Bitmap && args[1] is Rect && args[2] is RectF && args[3] is Paint) -> objVar.drawBitmap(args[0] as Bitmap, args[1] as Rect, args[2] as RectF, args[3] as Paint)
    (objVar is Canvas && method == "drawBitmap" && args.count() == 4 && args[0] is Bitmap && args[3] is Paint) -> objVar.drawBitmap(args[0] as Bitmap, anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Paint)
    (objVar is Canvas && method == "drawBitmap" && args.count() == 4 && args[0] is Bitmap && args[1] is Rect && args[2] is Rect && args[3] is Paint) -> objVar.drawBitmap(args[0] as Bitmap, args[1] as Rect, args[2] as Rect, args[3] as Paint)
    (objVar is Canvas && method == "drawBitmapMesh" && args.count() == 8 && args[0] is Bitmap && args[3] is FloatArray && args[5] is IntArray && args[7] is Paint) -> objVar.drawBitmapMesh(args[0] as Bitmap, anyToInt(args[1]), anyToInt(args[2]), args[3] as FloatArray, anyToInt(args[4]), args[5] as IntArray, anyToInt(args[6]), args[7] as Paint)
    (objVar is Canvas && method == "drawCircle" && args.count() == 4 && args[3] is Paint) -> objVar.drawCircle(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Paint)
    (objVar is Canvas && method == "drawColor" && args.count() == 1 ) -> objVar.drawColor(anyToInt(args[0]))
    (objVar is Canvas && method == "drawColor" && args.count() == 2 && args[1] is PorterDuff.Mode) -> objVar.drawColor(anyToInt(args[0]), args[1] as PorterDuff.Mode)
    (objVar is Canvas && method == "drawLine" && args.count() == 5 && args[4] is Paint) -> objVar.drawLine(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Paint)
    (objVar is Canvas && method == "drawLines" && args.count() == 2 && args[0] is FloatArray && args[1] is Paint) -> objVar.drawLines(args[0] as FloatArray, args[1] as Paint)
    (objVar is Canvas && method == "drawLines" && args.count() == 4 && args[0] is FloatArray && args[3] is Paint) -> objVar.drawLines(args[0] as FloatArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as Paint)
    (objVar is Canvas && method == "drawOval" && args.count() == 5 && args[4] is Paint) -> objVar.drawOval(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Paint)
    (objVar is Canvas && method == "drawOval" && args.count() == 2 && args[0] is RectF && args[1] is Paint) -> objVar.drawOval(args[0] as RectF, args[1] as Paint)
    (objVar is Canvas && method == "drawPaint" && args.count() == 1 && args[0] is Paint) -> objVar.drawPaint(args[0] as Paint)
    (objVar is Canvas && method == "drawPath" && args.count() == 2 && args[0] is Path && args[1] is Paint) -> objVar.drawPath(args[0] as Path, args[1] as Paint)
    (objVar is Canvas && method == "drawPicture" && args.count() == 2 && args[0] is Picture && args[1] is RectF) -> objVar.drawPicture(args[0] as Picture, args[1] as RectF)
    (objVar is Canvas && method == "drawPicture" && args.count() == 1 && args[0] is Picture) -> objVar.drawPicture(args[0] as Picture)
    (objVar is Canvas && method == "drawPicture" && args.count() == 2 && args[0] is Picture && args[1] is Rect) -> objVar.drawPicture(args[0] as Picture, args[1] as Rect)
    (objVar is Canvas && method == "drawPoint" && args.count() == 3 && args[2] is Paint) -> objVar.drawPoint(anyToFloat(args[0]), anyToFloat(args[1]), args[2] as Paint)
    (objVar is Canvas && method == "drawPoints" && args.count() == 4 && args[0] is FloatArray && args[3] is Paint) -> objVar.drawPoints(args[0] as FloatArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as Paint)
    (objVar is Canvas && method == "drawPoints" && args.count() == 2 && args[0] is FloatArray && args[1] is Paint) -> objVar.drawPoints(args[0] as FloatArray, args[1] as Paint)
    (objVar is Canvas && method == "drawPosText" && args.count() == 5 && args[0] is CharArray && args[3] is FloatArray && args[4] is Paint) -> objVar.drawPosText(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as FloatArray, args[4] as Paint)
    (objVar is Canvas && method == "drawPosText" && args.count() == 3 && args[0] is String && args[1] is FloatArray && args[2] is Paint) -> objVar.drawPosText(args[0] as String, args[1] as FloatArray, args[2] as Paint)
    (objVar is Canvas && method == "drawRGB" && args.count() == 3 ) -> objVar.drawRGB(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Canvas && method == "drawRect" && args.count() == 5 && args[4] is Paint) -> objVar.drawRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Paint)
    (objVar is Canvas && method == "drawRect" && args.count() == 2 && args[0] is RectF && args[1] is Paint) -> objVar.drawRect(args[0] as RectF, args[1] as Paint)
    (objVar is Canvas && method == "drawRect" && args.count() == 2 && args[0] is Rect && args[1] is Paint) -> objVar.drawRect(args[0] as Rect, args[1] as Paint)
    (objVar is Canvas && method == "drawRoundRect" && args.count() == 7 && args[6] is Paint) -> objVar.drawRoundRect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), anyToFloat(args[5]), args[6] as Paint)
    (objVar is Canvas && method == "drawRoundRect" && args.count() == 4 && args[0] is RectF && args[3] is Paint) -> objVar.drawRoundRect(args[0] as RectF, anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Paint)
    (objVar is Canvas && method == "drawText" && args.count() == 4 && args[0] is String && args[3] is Paint) -> objVar.drawText(args[0] as String, anyToFloat(args[1]), anyToFloat(args[2]), args[3] as Paint)
    (objVar is Canvas && method == "drawText" && args.count() == 6 && args[0] is CharSequence && args[5] is Paint) -> objVar.drawText(args[0] as CharSequence, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), args[5] as Paint)
    (objVar is Canvas && method == "drawText" && args.count() == 6 && args[0] is CharArray && args[5] is Paint) -> objVar.drawText(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), args[5] as Paint)
    (objVar is Canvas && method == "drawText" && args.count() == 6 && args[0] is String && args[5] is Paint) -> objVar.drawText(args[0] as String, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), args[5] as Paint)
    (objVar is Canvas && method == "drawTextOnPath" && args.count() == 5 && args[0] is String && args[1] is Path && args[4] is Paint) -> objVar.drawTextOnPath(args[0] as String, args[1] as Path, anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Paint)
    (objVar is Canvas && method == "drawTextOnPath" && args.count() == 7 && args[0] is CharArray && args[3] is Path && args[6] is Paint) -> objVar.drawTextOnPath(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as Path, anyToFloat(args[4]), anyToFloat(args[5]), args[6] as Paint)
    (objVar is Canvas && method == "drawVertices" && args.count() == 12 && args[0] is Canvas.VertexMode && args[2] is FloatArray && args[4] is FloatArray && args[6] is IntArray && args[8] is ShortArray && args[11] is Paint) -> objVar.drawVertices(args[0] as Canvas.VertexMode, anyToInt(args[1]), args[2] as FloatArray, anyToInt(args[3]), args[4] as FloatArray, anyToInt(args[5]), args[6] as IntArray, anyToInt(args[7]), args[8] as ShortArray, anyToInt(args[9]), anyToInt(args[10]), args[11] as Paint)
    (objVar is Canvas && method == "getClipBounds" && args.count() == 0 ) -> objVar.getClipBounds()
    (objVar is Canvas && method == "getClipBounds" && args.count() == 1 && args[0] is Rect) -> objVar.getClipBounds(args[0] as Rect)
    (objVar is Canvas && method == "getDensity" && args.count() == 0 ) -> objVar.getDensity()
    (objVar is Canvas && method == "getDrawFilter" && args.count() == 0 ) -> objVar.getDrawFilter()
    (objVar is Canvas && method == "getHeight" && args.count() == 0 ) -> objVar.getHeight()
    (objVar is Canvas && method == "getMatrix" && args.count() == 1 && args[0] is Matrix) -> objVar.getMatrix(args[0] as Matrix)
    (objVar is Canvas && method == "getMatrix" && args.count() == 0 ) -> objVar.getMatrix()
    (objVar is Canvas && method == "getMaximumBitmapHeight" && args.count() == 0 ) -> objVar.getMaximumBitmapHeight()
    (objVar is Canvas && method == "getMaximumBitmapWidth" && args.count() == 0 ) -> objVar.getMaximumBitmapWidth()
    (objVar is Canvas && method == "getSaveCount" && args.count() == 0 ) -> objVar.getSaveCount()
    (objVar is Canvas && method == "getWidth" && args.count() == 0 ) -> objVar.getWidth()
    (objVar is Canvas && method == "isHardwareAccelerated" && args.count() == 0 ) -> objVar.isHardwareAccelerated()
    (objVar is Canvas && method == "isOpaque" && args.count() == 0 ) -> objVar.isOpaque()
    (objVar is Canvas && method == "quickReject" && args.count() == 2 && args[0] is Path && args[1] is Canvas.EdgeType) -> objVar.quickReject(args[0] as Path, args[1] as Canvas.EdgeType)
    (objVar is Canvas && method == "quickReject" && args.count() == 5 && args[4] is Canvas.EdgeType) -> objVar.quickReject(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Canvas.EdgeType)
    (objVar is Canvas && method == "quickReject" && args.count() == 2 && args[0] is RectF && args[1] is Canvas.EdgeType) -> objVar.quickReject(args[0] as RectF, args[1] as Canvas.EdgeType)
    (objVar is Canvas && method == "restore" && args.count() == 0 ) -> objVar.restore()
    (objVar is Canvas && method == "restoreToCount" && args.count() == 1 ) -> objVar.restoreToCount(anyToInt(args[0]))
    (objVar is Canvas && method == "rotate" && args.count() == 1 ) -> objVar.rotate(anyToFloat(args[0]))
    (objVar is Canvas && method == "rotate" && args.count() == 3 ) -> objVar.rotate(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Canvas && method == "save" && args.count() == 0 ) -> objVar.save()
    (objVar is Canvas && method == "save" && args.count() == 1 ) -> objVar.save(anyToInt(args[0]))
    (objVar is Canvas && method == "saveLayer" && args.count() == 3 && args[0] is RectF && args[1] is Paint) -> objVar.saveLayer(args[0] as RectF, args[1] as Paint, anyToInt(args[2]))
    (objVar is Canvas && method == "saveLayer" && args.count() == 2 && args[0] is RectF && args[1] is Paint) -> objVar.saveLayer(args[0] as RectF, args[1] as Paint)
    (objVar is Canvas && method == "saveLayer" && args.count() == 5 && args[4] is Paint) -> objVar.saveLayer(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Paint)
    (objVar is Canvas && method == "saveLayer" && args.count() == 6 && args[4] is Paint) -> objVar.saveLayer(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), args[4] as Paint, anyToInt(args[5]))
    (objVar is Canvas && method == "saveLayerAlpha" && args.count() == 3 && args[0] is RectF) -> objVar.saveLayerAlpha(args[0] as RectF, anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Canvas && method == "saveLayerAlpha" && args.count() == 2 && args[0] is RectF) -> objVar.saveLayerAlpha(args[0] as RectF, anyToInt(args[1]))
    (objVar is Canvas && method == "saveLayerAlpha" && args.count() == 6 ) -> objVar.saveLayerAlpha(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToInt(args[4]), anyToInt(args[5]))
    (objVar is Canvas && method == "saveLayerAlpha" && args.count() == 5 ) -> objVar.saveLayerAlpha(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]), anyToInt(args[4]))
    (objVar is Canvas && method == "scale" && args.count() == 2 ) -> objVar.scale(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Canvas && method == "scale" && args.count() == 4 ) -> objVar.scale(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Canvas && method == "setBitmap" && args.count() == 1 && args[0] is Bitmap) -> objVar.setBitmap(args[0] as Bitmap)
    (objVar is Canvas && method == "setDensity" && args.count() == 1 ) -> objVar.setDensity(anyToInt(args[0]))
    (objVar is Canvas && method == "setDrawFilter" && args.count() == 1 && args[0] is DrawFilter) -> objVar.setDrawFilter(args[0] as DrawFilter)
    (objVar is Canvas && method == "setMatrix" && args.count() == 1 && args[0] is Matrix) -> objVar.setMatrix(args[0] as Matrix)
    (objVar is Canvas && method == "skew" && args.count() == 2 ) -> objVar.skew(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Canvas && method == "translate" && args.count() == 2 ) -> objVar.translate(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar == "Matrix\$ScaleToFit" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Matrix.ScaleToFit.valueOf(args[0] as String)
    (objVar == "Matrix\$ScaleToFit" && method == "values" && args.count() == 0 ) -> Matrix.ScaleToFit.values()
    (objVar is Bitmap && method == "compress" && args.count() == 3 && args[0] is Bitmap.CompressFormat && args[2] is OutputStream) -> objVar.compress(args[0] as Bitmap.CompressFormat, anyToInt(args[1]), args[2] as OutputStream)
    (objVar is Bitmap && method == "copy" && args.count() == 2 && args[0] is Bitmap.Config && args[1] is Boolean) -> objVar.copy(args[0] as Bitmap.Config, args[1] as Boolean)
    (objVar is Bitmap && method == "copyPixelsFromBuffer" && args.count() == 1 && args[0] is Buffer) -> objVar.copyPixelsFromBuffer(args[0] as Buffer)
    (objVar is Bitmap && method == "copyPixelsToBuffer" && args.count() == 1 && args[0] is Buffer) -> objVar.copyPixelsToBuffer(args[0] as Buffer)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 5 && args[0] is DisplayMetrics && args[1] is IntArray && args[4] is Bitmap.Config) -> Bitmap.createBitmap(args[0] as DisplayMetrics, args[1] as IntArray, anyToInt(args[2]), anyToInt(args[3]), args[4] as Bitmap.Config)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 7 && args[0] is DisplayMetrics && args[1] is IntArray && args[6] is Bitmap.Config) -> Bitmap.createBitmap(args[0] as DisplayMetrics, args[1] as IntArray, anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]), anyToInt(args[5]), args[6] as Bitmap.Config)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 5 && args[0] is Bitmap) -> Bitmap.createBitmap(args[0] as Bitmap, anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]))
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 1 && args[0] is Bitmap) -> Bitmap.createBitmap(args[0] as Bitmap)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 4 && args[0] is DisplayMetrics && args[3] is Bitmap.Config) -> Bitmap.createBitmap(args[0] as DisplayMetrics, anyToInt(args[1]), anyToInt(args[2]), args[3] as Bitmap.Config)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 7 && args[0] is Bitmap && args[5] is Matrix && args[6] is Boolean) -> Bitmap.createBitmap(args[0] as Bitmap, anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]), args[5] as Matrix, args[6] as Boolean)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 3 && args[2] is Bitmap.Config) -> Bitmap.createBitmap(anyToInt(args[0]), anyToInt(args[1]), args[2] as Bitmap.Config)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 6 && args[0] is IntArray && args[5] is Bitmap.Config) -> Bitmap.createBitmap(args[0] as IntArray, anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]), args[5] as Bitmap.Config)
    (objVar == "Bitmap" && method == "createBitmap" && args.count() == 4 && args[0] is IntArray && args[3] is Bitmap.Config) -> Bitmap.createBitmap(args[0] as IntArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as Bitmap.Config)
    (objVar == "Bitmap" && method == "createScaledBitmap" && args.count() == 4 && args[0] is Bitmap && args[3] is Boolean) -> Bitmap.createScaledBitmap(args[0] as Bitmap, anyToInt(args[1]), anyToInt(args[2]), args[3] as Boolean)
    (objVar is Bitmap && method == "describeContents" && args.count() == 0 ) -> objVar.describeContents()
    (objVar is Bitmap && method == "eraseColor" && args.count() == 1 ) -> objVar.eraseColor(anyToInt(args[0]))
    (objVar is Bitmap && method == "extractAlpha" && args.count() == 0 ) -> objVar.extractAlpha()
    (objVar is Bitmap && method == "extractAlpha" && args.count() == 2 && args[0] is Paint && args[1] is IntArray) -> objVar.extractAlpha(args[0] as Paint, args[1] as IntArray)
    (objVar is Bitmap && method == "getAllocationByteCount" && args.count() == 0 ) -> objVar.getAllocationByteCount()
    (objVar is Bitmap && method == "getByteCount" && args.count() == 0 ) -> objVar.getByteCount()
    (objVar is Bitmap && method == "getConfig" && args.count() == 0 ) -> objVar.getConfig()
    (objVar is Bitmap && method == "getDensity" && args.count() == 0 ) -> objVar.getDensity()
    (objVar is Bitmap && method == "getGenerationId" && args.count() == 0 ) -> objVar.getGenerationId()
    (objVar is Bitmap && method == "getHeight" && args.count() == 0 ) -> objVar.getHeight()
    (objVar is Bitmap && method == "getNinePatchChunk" && args.count() == 0 ) -> objVar.getNinePatchChunk()
    (objVar is Bitmap && method == "getPixel" && args.count() == 2 ) -> objVar.getPixel(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Bitmap && method == "getPixels" && args.count() == 7 && args[0] is IntArray) -> objVar.getPixels(args[0] as IntArray, anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]), anyToInt(args[5]), anyToInt(args[6]))
    (objVar is Bitmap && method == "getRowBytes" && args.count() == 0 ) -> objVar.getRowBytes()
    (objVar is Bitmap && method == "getScaledHeight" && args.count() == 1 && args[0] is DisplayMetrics) -> objVar.getScaledHeight(args[0] as DisplayMetrics)
    (objVar is Bitmap && method == "getScaledHeight" && args.count() == 1 ) -> objVar.getScaledHeight(anyToInt(args[0]))
    (objVar is Bitmap && method == "getScaledHeight" && args.count() == 1 && args[0] is Canvas) -> objVar.getScaledHeight(args[0] as Canvas)
    (objVar is Bitmap && method == "getScaledWidth" && args.count() == 1 ) -> objVar.getScaledWidth(anyToInt(args[0]))
    (objVar is Bitmap && method == "getScaledWidth" && args.count() == 1 && args[0] is DisplayMetrics) -> objVar.getScaledWidth(args[0] as DisplayMetrics)
    (objVar is Bitmap && method == "getScaledWidth" && args.count() == 1 && args[0] is Canvas) -> objVar.getScaledWidth(args[0] as Canvas)
    (objVar is Bitmap && method == "getWidth" && args.count() == 0 ) -> objVar.getWidth()
    (objVar is Bitmap && method == "hasAlpha" && args.count() == 0 ) -> objVar.hasAlpha()
    (objVar is Bitmap && method == "hasMipMap" && args.count() == 0 ) -> objVar.hasMipMap()
    (objVar is Bitmap && method == "isMutable" && args.count() == 0 ) -> objVar.isMutable()
    (objVar is Bitmap && method == "isPremultiplied" && args.count() == 0 ) -> objVar.isPremultiplied()
    (objVar is Bitmap && method == "isRecycled" && args.count() == 0 ) -> objVar.isRecycled()
    (objVar is Bitmap && method == "prepareToDraw" && args.count() == 0 ) -> objVar.prepareToDraw()
    (objVar is Bitmap && method == "reconfigure" && args.count() == 3 && args[2] is Bitmap.Config) -> objVar.reconfigure(anyToInt(args[0]), anyToInt(args[1]), args[2] as Bitmap.Config)
    (objVar is Bitmap && method == "recycle" && args.count() == 0 ) -> objVar.recycle()
    (objVar is Bitmap && method == "sameAs" && args.count() == 1 && args[0] is Bitmap) -> objVar.sameAs(args[0] as Bitmap)
    (objVar is Bitmap && method == "setConfig" && args.count() == 1 && args[0] is Bitmap.Config) -> objVar.setConfig(args[0] as Bitmap.Config)
    (objVar is Bitmap && method == "setDensity" && args.count() == 1 ) -> objVar.setDensity(anyToInt(args[0]))
    (objVar is Bitmap && method == "setHasAlpha" && args.count() == 1 && args[0] is Boolean) -> objVar.setHasAlpha(args[0] as Boolean)
    (objVar is Bitmap && method == "setHasMipMap" && args.count() == 1 && args[0] is Boolean) -> objVar.setHasMipMap(args[0] as Boolean)
    (objVar is Bitmap && method == "setHeight" && args.count() == 1 ) -> objVar.setHeight(anyToInt(args[0]))
    (objVar is Bitmap && method == "setPixel" && args.count() == 3 ) -> objVar.setPixel(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Bitmap && method == "setPixels" && args.count() == 7 && args[0] is IntArray) -> objVar.setPixels(args[0] as IntArray, anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), anyToInt(args[4]), anyToInt(args[5]), anyToInt(args[6]))
    (objVar is Bitmap && method == "setPremultiplied" && args.count() == 1 && args[0] is Boolean) -> objVar.setPremultiplied(args[0] as Boolean)
    (objVar is Bitmap && method == "setWidth" && args.count() == 1 ) -> objVar.setWidth(anyToInt(args[0]))
    (objVar is Bitmap && method == "writeToParcel" && args.count() == 2 && args[0] is Parcel) -> objVar.writeToParcel(args[0] as Parcel, anyToInt(args[1]))
    (objVar == "Bitmap\$Config" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Bitmap.Config.valueOf(args[0] as String)
    (objVar == "Bitmap\$Config" && method == "values" && args.count() == 0 ) -> Bitmap.Config.values()
    (objVar == "Canvas\$VertexMode" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Canvas.VertexMode.valueOf(args[0] as String)
    (objVar == "Canvas\$VertexMode" && method == "values" && args.count() == 0 ) -> Canvas.VertexMode.values()
    (objVar is Paint.FontMetricsInt && method == "toString" && args.count() == 0 ) -> objVar.toString()
    (objVar == "PorterDuff\$Mode" && method == "valueOf" && args.count() == 1 && args[0] is String) -> PorterDuff.Mode.valueOf(args[0] as String)
    (objVar == "PorterDuff\$Mode" && method == "values" && args.count() == 0 ) -> PorterDuff.Mode.values()
    (objVar is RectF && method == "centerX" && args.count() == 0 ) -> objVar.centerX()
    (objVar is RectF && method == "centerY" && args.count() == 0 ) -> objVar.centerY()
    (objVar is RectF && method == "contains" && args.count() == 4 ) -> objVar.contains(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is RectF && method == "contains" && args.count() == 2 ) -> objVar.contains(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is RectF && method == "contains" && args.count() == 1 && args[0] is RectF) -> objVar.contains(args[0] as RectF)
    (objVar is RectF && method == "describeContents" && args.count() == 0 ) -> objVar.describeContents()
    (objVar is RectF && method == "equals" && args.count() == 1 && args[0] is Any) -> objVar.equals(args[0] as Any)
    (objVar is RectF && method == "hashCode" && args.count() == 0 ) -> objVar.hashCode()
    (objVar is RectF && method == "height" && args.count() == 0 ) -> objVar.height()
    (objVar is RectF && method == "inset" && args.count() == 2 ) -> objVar.inset(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is RectF && method == "intersect" && args.count() == 4 ) -> objVar.intersect(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is RectF && method == "intersect" && args.count() == 1 && args[0] is RectF) -> objVar.intersect(args[0] as RectF)
    (objVar == "RectF" && method == "intersects" && args.count() == 2 && args[0] is RectF && args[1] is RectF) -> RectF.intersects(args[0] as RectF, args[1] as RectF)
    (objVar is RectF && method == "intersects" && args.count() == 4 ) -> objVar.intersects(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is RectF && method == "isEmpty" && args.count() == 0 ) -> objVar.isEmpty()
    (objVar is RectF && method == "offset" && args.count() == 2 ) -> objVar.offset(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is RectF && method == "offsetTo" && args.count() == 2 ) -> objVar.offsetTo(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is RectF && method == "readFromParcel" && args.count() == 1 && args[0] is Parcel) -> objVar.readFromParcel(args[0] as Parcel)
    (objVar is RectF && method == "round" && args.count() == 1 && args[0] is Rect) -> objVar.round(args[0] as Rect)
    (objVar is RectF && method == "roundOut" && args.count() == 1 && args[0] is Rect) -> objVar.roundOut(args[0] as Rect)
    (objVar is RectF && method == "set" && args.count() == 4 ) -> objVar.set(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is RectF && method == "set" && args.count() == 1 && args[0] is RectF) -> objVar.set(args[0] as RectF)
    (objVar is RectF && method == "set" && args.count() == 1 && args[0] is Rect) -> objVar.set(args[0] as Rect)
    (objVar is RectF && method == "setEmpty" && args.count() == 0 ) -> objVar.setEmpty()
    (objVar is RectF && method == "setIntersect" && args.count() == 2 && args[0] is RectF && args[1] is RectF) -> objVar.setIntersect(args[0] as RectF, args[1] as RectF)
    (objVar is RectF && method == "sort" && args.count() == 0 ) -> objVar.sort()
    (objVar is RectF && method == "toShortString" && args.count() == 0 ) -> objVar.toShortString()
    (objVar is RectF && method == "toString" && args.count() == 0 ) -> objVar.toString()
    (objVar is RectF && method == "union" && args.count() == 4 ) -> objVar.union(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is RectF && method == "union" && args.count() == 1 && args[0] is RectF) -> objVar.union(args[0] as RectF)
    (objVar is RectF && method == "union" && args.count() == 2 ) -> objVar.union(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is RectF && method == "width" && args.count() == 0 ) -> objVar.width()
    (objVar is RectF && method == "writeToParcel" && args.count() == 2 && args[0] is Parcel) -> objVar.writeToParcel(args[0] as Parcel, anyToInt(args[1]))
    (objVar == "Paint\$Join" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Paint.Join.valueOf(args[0] as String)
    (objVar == "Paint\$Join" && method == "values" && args.count() == 0 ) -> Paint.Join.values()
    (objVar is Matrix && method == "equals" && args.count() == 1 && args[0] is Any) -> objVar.equals(args[0] as Any)
    (objVar is Matrix && method == "getValues" && args.count() == 1 && args[0] is FloatArray) -> objVar.getValues(args[0] as FloatArray)
    (objVar is Matrix && method == "hashCode" && args.count() == 0 ) -> objVar.hashCode()
    (objVar is Matrix && method == "invert" && args.count() == 1 && args[0] is Matrix) -> objVar.invert(args[0] as Matrix)
    (objVar is Matrix && method == "isAffine" && args.count() == 0 ) -> objVar.isAffine()
    (objVar is Matrix && method == "isIdentity" && args.count() == 0 ) -> objVar.isIdentity()
    (objVar is Matrix && method == "mapPoints" && args.count() == 1 && args[0] is FloatArray) -> objVar.mapPoints(args[0] as FloatArray)
    (objVar is Matrix && method == "mapPoints" && args.count() == 5 && args[0] is FloatArray && args[2] is FloatArray) -> objVar.mapPoints(args[0] as FloatArray, anyToInt(args[1]), args[2] as FloatArray, anyToInt(args[3]), anyToInt(args[4]))
    (objVar is Matrix && method == "mapPoints" && args.count() == 2 && args[0] is FloatArray && args[1] is FloatArray) -> objVar.mapPoints(args[0] as FloatArray, args[1] as FloatArray)
    (objVar is Matrix && method == "mapRadius" && args.count() == 1 ) -> objVar.mapRadius(anyToFloat(args[0]))
    (objVar is Matrix && method == "mapRect" && args.count() == 2 && args[0] is RectF && args[1] is RectF) -> objVar.mapRect(args[0] as RectF, args[1] as RectF)
    (objVar is Matrix && method == "mapRect" && args.count() == 1 && args[0] is RectF) -> objVar.mapRect(args[0] as RectF)
    (objVar is Matrix && method == "mapVectors" && args.count() == 2 && args[0] is FloatArray && args[1] is FloatArray) -> objVar.mapVectors(args[0] as FloatArray, args[1] as FloatArray)
    (objVar is Matrix && method == "mapVectors" && args.count() == 1 && args[0] is FloatArray) -> objVar.mapVectors(args[0] as FloatArray)
    (objVar is Matrix && method == "mapVectors" && args.count() == 5 && args[0] is FloatArray && args[2] is FloatArray) -> objVar.mapVectors(args[0] as FloatArray, anyToInt(args[1]), args[2] as FloatArray, anyToInt(args[3]), anyToInt(args[4]))
    (objVar is Matrix && method == "postConcat" && args.count() == 1 && args[0] is Matrix) -> objVar.postConcat(args[0] as Matrix)
    (objVar is Matrix && method == "postRotate" && args.count() == 1 ) -> objVar.postRotate(anyToFloat(args[0]))
    (objVar is Matrix && method == "postRotate" && args.count() == 3 ) -> objVar.postRotate(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Matrix && method == "postScale" && args.count() == 2 ) -> objVar.postScale(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "postScale" && args.count() == 4 ) -> objVar.postScale(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "postSkew" && args.count() == 4 ) -> objVar.postSkew(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "postSkew" && args.count() == 2 ) -> objVar.postSkew(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "postTranslate" && args.count() == 2 ) -> objVar.postTranslate(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "preConcat" && args.count() == 1 && args[0] is Matrix) -> objVar.preConcat(args[0] as Matrix)
    (objVar is Matrix && method == "preRotate" && args.count() == 1 ) -> objVar.preRotate(anyToFloat(args[0]))
    (objVar is Matrix && method == "preRotate" && args.count() == 3 ) -> objVar.preRotate(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Matrix && method == "preScale" && args.count() == 2 ) -> objVar.preScale(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "preScale" && args.count() == 4 ) -> objVar.preScale(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "preSkew" && args.count() == 4 ) -> objVar.preSkew(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "preSkew" && args.count() == 2 ) -> objVar.preSkew(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "preTranslate" && args.count() == 2 ) -> objVar.preTranslate(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "rectStaysRect" && args.count() == 0 ) -> objVar.rectStaysRect()
    (objVar is Matrix && method == "reset" && args.count() == 0 ) -> objVar.reset()
    (objVar is Matrix && method == "set" && args.count() == 1 && args[0] is Matrix) -> objVar.set(args[0] as Matrix)
    (objVar is Matrix && method == "setConcat" && args.count() == 2 && args[0] is Matrix && args[1] is Matrix) -> objVar.setConcat(args[0] as Matrix, args[1] as Matrix)
    (objVar is Matrix && method == "setPolyToPoly" && args.count() == 5 && args[0] is FloatArray && args[2] is FloatArray) -> objVar.setPolyToPoly(args[0] as FloatArray, anyToInt(args[1]), args[2] as FloatArray, anyToInt(args[3]), anyToInt(args[4]))
    (objVar is Matrix && method == "setRectToRect" && args.count() == 3 && args[0] is RectF && args[1] is RectF && args[2] is Matrix.ScaleToFit) -> objVar.setRectToRect(args[0] as RectF, args[1] as RectF, args[2] as Matrix.ScaleToFit)
    (objVar is Matrix && method == "setRotate" && args.count() == 1 ) -> objVar.setRotate(anyToFloat(args[0]))
    (objVar is Matrix && method == "setRotate" && args.count() == 3 ) -> objVar.setRotate(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]))
    (objVar is Matrix && method == "setScale" && args.count() == 4 ) -> objVar.setScale(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "setScale" && args.count() == 2 ) -> objVar.setScale(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "setSinCos" && args.count() == 2 ) -> objVar.setSinCos(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "setSinCos" && args.count() == 4 ) -> objVar.setSinCos(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "setSkew" && args.count() == 4 ) -> objVar.setSkew(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToFloat(args[3]))
    (objVar is Matrix && method == "setSkew" && args.count() == 2 ) -> objVar.setSkew(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "setTranslate" && args.count() == 2 ) -> objVar.setTranslate(anyToFloat(args[0]), anyToFloat(args[1]))
    (objVar is Matrix && method == "setValues" && args.count() == 1 && args[0] is FloatArray) -> objVar.setValues(args[0] as FloatArray)
    (objVar is Matrix && method == "toShortString" && args.count() == 0 ) -> objVar.toShortString()
    (objVar is Matrix && method == "toString" && args.count() == 0 ) -> objVar.toString()
    (objVar is Rect && method == "centerX" && args.count() == 0 ) -> objVar.centerX()
    (objVar is Rect && method == "centerY" && args.count() == 0 ) -> objVar.centerY()
    (objVar is Rect && method == "contains" && args.count() == 2 ) -> objVar.contains(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Rect && method == "contains" && args.count() == 4 ) -> objVar.contains(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Rect && method == "contains" && args.count() == 1 && args[0] is Rect) -> objVar.contains(args[0] as Rect)
    (objVar is Rect && method == "describeContents" && args.count() == 0 ) -> objVar.describeContents()
    (objVar is Rect && method == "equals" && args.count() == 1 && args[0] is Any) -> objVar.equals(args[0] as Any)
    (objVar is Rect && method == "exactCenterX" && args.count() == 0 ) -> objVar.exactCenterX()
    (objVar is Rect && method == "exactCenterY" && args.count() == 0 ) -> objVar.exactCenterY()
    (objVar is Rect && method == "flattenToString" && args.count() == 0 ) -> objVar.flattenToString()
    (objVar is Rect && method == "hashCode" && args.count() == 0 ) -> objVar.hashCode()
    (objVar is Rect && method == "height" && args.count() == 0 ) -> objVar.height()
    (objVar is Rect && method == "inset" && args.count() == 2 ) -> objVar.inset(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Rect && method == "intersect" && args.count() == 1 && args[0] is Rect) -> objVar.intersect(args[0] as Rect)
    (objVar is Rect && method == "intersect" && args.count() == 4 ) -> objVar.intersect(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Rect && method == "intersects" && args.count() == 4 ) -> objVar.intersects(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar == "Rect" && method == "intersects" && args.count() == 2 && args[0] is Rect && args[1] is Rect) -> Rect.intersects(args[0] as Rect, args[1] as Rect)
    (objVar is Rect && method == "isEmpty" && args.count() == 0 ) -> objVar.isEmpty()
    (objVar is Rect && method == "offset" && args.count() == 2 ) -> objVar.offset(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Rect && method == "offsetTo" && args.count() == 2 ) -> objVar.offsetTo(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Rect && method == "readFromParcel" && args.count() == 1 && args[0] is Parcel) -> objVar.readFromParcel(args[0] as Parcel)
    (objVar is Rect && method == "set" && args.count() == 4 ) -> objVar.set(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Rect && method == "set" && args.count() == 1 && args[0] is Rect) -> objVar.set(args[0] as Rect)
    (objVar is Rect && method == "setEmpty" && args.count() == 0 ) -> objVar.setEmpty()
    (objVar is Rect && method == "setIntersect" && args.count() == 2 && args[0] is Rect && args[1] is Rect) -> objVar.setIntersect(args[0] as Rect, args[1] as Rect)
    (objVar is Rect && method == "sort" && args.count() == 0 ) -> objVar.sort()
    (objVar is Rect && method == "toShortString" && args.count() == 0 ) -> objVar.toShortString()
    (objVar is Rect && method == "toString" && args.count() == 0 ) -> objVar.toString()
    (objVar == "Rect" && method == "unflattenFromString" && args.count() == 1 && args[0] is String) -> Rect.unflattenFromString(args[0] as String)
    (objVar is Rect && method == "union" && args.count() == 4 ) -> objVar.union(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Rect && method == "union" && args.count() == 1 && args[0] is Rect) -> objVar.union(args[0] as Rect)
    (objVar is Rect && method == "union" && args.count() == 2 ) -> objVar.union(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Rect && method == "width" && args.count() == 0 ) -> objVar.width()
    (objVar is Rect && method == "writeToParcel" && args.count() == 2 && args[0] is Parcel) -> objVar.writeToParcel(args[0] as Parcel, anyToInt(args[1]))
    (objVar == "Region\$Op" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Region.Op.valueOf(args[0] as String)
    (objVar == "Region\$Op" && method == "values" && args.count() == 0 ) -> Region.Op.values()
    (objVar == "Paint\$Cap" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Paint.Cap.valueOf(args[0] as String)
    (objVar == "Paint\$Cap" && method == "values" && args.count() == 0 ) -> Paint.Cap.values()
    (objVar == "Path\$FillType" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Path.FillType.valueOf(args[0] as String)
    (objVar == "Path\$FillType" && method == "values" && args.count() == 0 ) -> Path.FillType.values()
    (objVar is Region && method == "contains" && args.count() == 2 ) -> objVar.contains(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Region && method == "describeContents" && args.count() == 0 ) -> objVar.describeContents()
    (objVar is Region && method == "equals" && args.count() == 1 && args[0] is Any) -> objVar.equals(args[0] as Any)
    (objVar is Region && method == "getBoundaryPath" && args.count() == 0 ) -> objVar.getBoundaryPath()
    (objVar is Region && method == "getBoundaryPath" && args.count() == 1 && args[0] is Path) -> objVar.getBoundaryPath(args[0] as Path)
    (objVar is Region && method == "getBounds" && args.count() == 1 && args[0] is Rect) -> objVar.getBounds(args[0] as Rect)
    (objVar is Region && method == "getBounds" && args.count() == 0 ) -> objVar.getBounds()
    (objVar is Region && method == "isComplex" && args.count() == 0 ) -> objVar.isComplex()
    (objVar is Region && method == "isEmpty" && args.count() == 0 ) -> objVar.isEmpty()
    (objVar is Region && method == "isRect" && args.count() == 0 ) -> objVar.isRect()
    (objVar is Region && method == "op" && args.count() == 2 && args[0] is Rect && args[1] is Region.Op) -> objVar.op(args[0] as Rect, args[1] as Region.Op)
    (objVar is Region && method == "op" && args.count() == 5 && args[4] is Region.Op) -> objVar.op(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]), args[4] as Region.Op)
    (objVar is Region && method == "op" && args.count() == 2 && args[0] is Region && args[1] is Region.Op) -> objVar.op(args[0] as Region, args[1] as Region.Op)
    (objVar is Region && method == "op" && args.count() == 3 && args[0] is Rect && args[1] is Region && args[2] is Region.Op) -> objVar.op(args[0] as Rect, args[1] as Region, args[2] as Region.Op)
    (objVar is Region && method == "op" && args.count() == 3 && args[0] is Region && args[1] is Region && args[2] is Region.Op) -> objVar.op(args[0] as Region, args[1] as Region, args[2] as Region.Op)
    (objVar is Region && method == "quickContains" && args.count() == 4 ) -> objVar.quickContains(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Region && method == "quickContains" && args.count() == 1 && args[0] is Rect) -> objVar.quickContains(args[0] as Rect)
    (objVar is Region && method == "quickReject" && args.count() == 4 ) -> objVar.quickReject(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Region && method == "quickReject" && args.count() == 1 && args[0] is Region) -> objVar.quickReject(args[0] as Region)
    (objVar is Region && method == "quickReject" && args.count() == 1 && args[0] is Rect) -> objVar.quickReject(args[0] as Rect)
    (objVar is Region && method == "set" && args.count() == 4 ) -> objVar.set(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Region && method == "set" && args.count() == 1 && args[0] is Rect) -> objVar.set(args[0] as Rect)
    (objVar is Region && method == "set" && args.count() == 1 && args[0] is Region) -> objVar.set(args[0] as Region)
    (objVar is Region && method == "setEmpty" && args.count() == 0 ) -> objVar.setEmpty()
    (objVar is Region && method == "setPath" && args.count() == 2 && args[0] is Path && args[1] is Region) -> objVar.setPath(args[0] as Path, args[1] as Region)
    (objVar is Region && method == "toString" && args.count() == 0 ) -> objVar.toString()
    (objVar is Region && method == "translate" && args.count() == 3 && args[2] is Region) -> objVar.translate(anyToInt(args[0]), anyToInt(args[1]), args[2] as Region)
    (objVar is Region && method == "translate" && args.count() == 2 ) -> objVar.translate(anyToInt(args[0]), anyToInt(args[1]))
    (objVar is Region && method == "union" && args.count() == 1 && args[0] is Rect) -> objVar.union(args[0] as Rect)
    (objVar is Region && method == "writeToParcel" && args.count() == 2 && args[0] is Parcel) -> objVar.writeToParcel(args[0] as Parcel, anyToInt(args[1]))
    (objVar == "Color" && method == "HSVToColor" && args.count() == 1 && args[0] is FloatArray) -> Color.HSVToColor(args[0] as FloatArray)
    (objVar == "Color" && method == "HSVToColor" && args.count() == 2 && args[1] is FloatArray) -> Color.HSVToColor(anyToInt(args[0]), args[1] as FloatArray)
    (objVar == "Color" && method == "RGBToHSV" && args.count() == 4 && args[3] is FloatArray) -> Color.RGBToHSV(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), args[3] as FloatArray)
    (objVar == "Color" && method == "alpha" && args.count() == 1 ) -> Color.alpha(anyToInt(args[0]))
    (objVar == "Color" && method == "argb" && args.count() == 4 ) -> Color.argb(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar == "Color" && method == "blue" && args.count() == 1 ) -> Color.blue(anyToInt(args[0]))
    (objVar == "Color" && method == "colorToHSV" && args.count() == 2 && args[1] is FloatArray) -> Color.colorToHSV(anyToInt(args[0]), args[1] as FloatArray)
    (objVar == "Color" && method == "green" && args.count() == 1 ) -> Color.green(anyToInt(args[0]))
    (objVar == "Color" && method == "parseColor" && args.count() == 1 && args[0] is String) -> Color.parseColor(args[0] as String)
    (objVar == "Color" && method == "red" && args.count() == 1 ) -> Color.red(anyToInt(args[0]))
    (objVar == "Color" && method == "rgb" && args.count() == 3 ) -> Color.rgb(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Paint && method == "ascent" && args.count() == 0 ) -> objVar.ascent()
    (objVar is Paint && method == "breakText" && args.count() == 5 && args[0] is CharArray && args[4] is FloatArray) -> objVar.breakText(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), args[4] as FloatArray)
    (objVar is Paint && method == "breakText" && args.count() == 6 && args[0] is CharSequence && args[3] is Boolean && args[5] is FloatArray) -> objVar.breakText(args[0] as CharSequence, anyToInt(args[1]), anyToInt(args[2]), args[3] as Boolean, anyToFloat(args[4]), args[5] as FloatArray)
    (objVar is Paint && method == "breakText" && args.count() == 4 && args[0] is String && args[1] is Boolean && args[3] is FloatArray) -> objVar.breakText(args[0] as String, args[1] as Boolean, anyToFloat(args[2]), args[3] as FloatArray)
    (objVar is Paint && method == "clearShadowLayer" && args.count() == 0 ) -> objVar.clearShadowLayer()
    (objVar is Paint && method == "descent" && args.count() == 0 ) -> objVar.descent()
    (objVar is Paint && method == "getAlpha" && args.count() == 0 ) -> objVar.getAlpha()
    (objVar is Paint && method == "getColor" && args.count() == 0 ) -> objVar.getColor()
    (objVar is Paint && method == "getColorFilter" && args.count() == 0 ) -> objVar.getColorFilter()
    (objVar is Paint && method == "getFillPath" && args.count() == 2 && args[0] is Path && args[1] is Path) -> objVar.getFillPath(args[0] as Path, args[1] as Path)
    (objVar is Paint && method == "getFlags" && args.count() == 0 ) -> objVar.getFlags()
    (objVar is Paint && method == "getFontFeatureSettings" && args.count() == 0 ) -> objVar.getFontFeatureSettings()
    (objVar is Paint && method == "getFontMetrics" && args.count() == 1 && args[0] is Paint.FontMetrics) -> objVar.getFontMetrics(args[0] as Paint.FontMetrics)
    (objVar is Paint && method == "getFontMetrics" && args.count() == 0 ) -> objVar.getFontMetrics()
    (objVar is Paint && method == "getFontMetricsInt" && args.count() == 0 ) -> objVar.getFontMetricsInt()
    (objVar is Paint && method == "getFontMetricsInt" && args.count() == 1 && args[0] is Paint.FontMetricsInt) -> objVar.getFontMetricsInt(args[0] as Paint.FontMetricsInt)
    (objVar is Paint && method == "getFontSpacing" && args.count() == 0 ) -> objVar.getFontSpacing()
    (objVar is Paint && method == "getHinting" && args.count() == 0 ) -> objVar.getHinting()
    (objVar is Paint && method == "getLetterSpacing" && args.count() == 0 ) -> objVar.getLetterSpacing()
    (objVar is Paint && method == "getMaskFilter" && args.count() == 0 ) -> objVar.getMaskFilter()
    (objVar is Paint && method == "getPathEffect" && args.count() == 0 ) -> objVar.getPathEffect()
    (objVar is Paint && method == "getRasterizer" && args.count() == 0 ) -> objVar.getRasterizer()
    (objVar is Paint && method == "getShader" && args.count() == 0 ) -> objVar.getShader()
    (objVar is Paint && method == "getStrokeCap" && args.count() == 0 ) -> objVar.getStrokeCap()
    (objVar is Paint && method == "getStrokeJoin" && args.count() == 0 ) -> objVar.getStrokeJoin()
    (objVar is Paint && method == "getStrokeMiter" && args.count() == 0 ) -> objVar.getStrokeMiter()
    (objVar is Paint && method == "getStrokeWidth" && args.count() == 0 ) -> objVar.getStrokeWidth()
    (objVar is Paint && method == "getStyle" && args.count() == 0 ) -> objVar.getStyle()
    (objVar is Paint && method == "getTextAlign" && args.count() == 0 ) -> objVar.getTextAlign()
    (objVar is Paint && method == "getTextBounds" && args.count() == 4 && args[0] is CharArray && args[3] is Rect) -> objVar.getTextBounds(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as Rect)
    (objVar is Paint && method == "getTextBounds" && args.count() == 4 && args[0] is String && args[3] is Rect) -> objVar.getTextBounds(args[0] as String, anyToInt(args[1]), anyToInt(args[2]), args[3] as Rect)
    (objVar is Paint && method == "getTextLocale" && args.count() == 0 ) -> objVar.getTextLocale()
    (objVar is Paint && method == "getTextPath" && args.count() == 6 && args[0] is CharArray && args[5] is Path) -> objVar.getTextPath(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), args[5] as Path)
    (objVar is Paint && method == "getTextPath" && args.count() == 6 && args[0] is String && args[5] is Path) -> objVar.getTextPath(args[0] as String, anyToInt(args[1]), anyToInt(args[2]), anyToFloat(args[3]), anyToFloat(args[4]), args[5] as Path)
    (objVar is Paint && method == "getTextScaleX" && args.count() == 0 ) -> objVar.getTextScaleX()
    (objVar is Paint && method == "getTextSize" && args.count() == 0 ) -> objVar.getTextSize()
    (objVar is Paint && method == "getTextSkewX" && args.count() == 0 ) -> objVar.getTextSkewX()
    (objVar is Paint && method == "getTextWidths" && args.count() == 4 && args[0] is String && args[3] is FloatArray) -> objVar.getTextWidths(args[0] as String, anyToInt(args[1]), anyToInt(args[2]), args[3] as FloatArray)
    (objVar is Paint && method == "getTextWidths" && args.count() == 2 && args[0] is String && args[1] is FloatArray) -> objVar.getTextWidths(args[0] as String, args[1] as FloatArray)
    (objVar is Paint && method == "getTextWidths" && args.count() == 4 && args[0] is CharSequence && args[3] is FloatArray) -> objVar.getTextWidths(args[0] as CharSequence, anyToInt(args[1]), anyToInt(args[2]), args[3] as FloatArray)
    (objVar is Paint && method == "getTextWidths" && args.count() == 4 && args[0] is CharArray && args[3] is FloatArray) -> objVar.getTextWidths(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]), args[3] as FloatArray)
    (objVar is Paint && method == "getTypeface" && args.count() == 0 ) -> objVar.getTypeface()
    (objVar is Paint && method == "getXfermode" && args.count() == 0 ) -> objVar.getXfermode()
    (objVar is Paint && method == "isAntiAlias" && args.count() == 0 ) -> objVar.isAntiAlias()
    (objVar is Paint && method == "isDither" && args.count() == 0 ) -> objVar.isDither()
    (objVar is Paint && method == "isElegantTextHeight" && args.count() == 0 ) -> objVar.isElegantTextHeight()
    (objVar is Paint && method == "isFakeBoldText" && args.count() == 0 ) -> objVar.isFakeBoldText()
    (objVar is Paint && method == "isFilterBitmap" && args.count() == 0 ) -> objVar.isFilterBitmap()
    (objVar is Paint && method == "isLinearText" && args.count() == 0 ) -> objVar.isLinearText()
    (objVar is Paint && method == "isStrikeThruText" && args.count() == 0 ) -> objVar.isStrikeThruText()
    (objVar is Paint && method == "isSubpixelText" && args.count() == 0 ) -> objVar.isSubpixelText()
    (objVar is Paint && method == "isUnderlineText" && args.count() == 0 ) -> objVar.isUnderlineText()
    (objVar is Paint && method == "measureText" && args.count() == 1 && args[0] is String) -> objVar.measureText(args[0] as String)
    (objVar is Paint && method == "measureText" && args.count() == 3 && args[0] is CharSequence) -> objVar.measureText(args[0] as CharSequence, anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Paint && method == "measureText" && args.count() == 3 && args[0] is String) -> objVar.measureText(args[0] as String, anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Paint && method == "measureText" && args.count() == 3 && args[0] is CharArray) -> objVar.measureText(args[0] as CharArray, anyToInt(args[1]), anyToInt(args[2]))
    (objVar is Paint && method == "reset" && args.count() == 0 ) -> objVar.reset()
    (objVar is Paint && method == "set" && args.count() == 1 && args[0] is Paint) -> objVar.set(args[0] as Paint)
    (objVar is Paint && method == "setARGB" && args.count() == 4 ) -> objVar.setARGB(anyToInt(args[0]), anyToInt(args[1]), anyToInt(args[2]), anyToInt(args[3]))
    (objVar is Paint && method == "setAlpha" && args.count() == 1 ) -> objVar.setAlpha(anyToInt(args[0]))
    (objVar is Paint && method == "setAntiAlias" && args.count() == 1 && args[0] is Boolean) -> objVar.setAntiAlias(args[0] as Boolean)
    (objVar is Paint && method == "setColor" && args.count() == 1 ) -> objVar.setColor(anyToInt(args[0]))
    (objVar is Paint && method == "setColorFilter" && args.count() == 1 && args[0] is ColorFilter) -> objVar.setColorFilter(args[0] as ColorFilter)
    (objVar is Paint && method == "setDither" && args.count() == 1 && args[0] is Boolean) -> objVar.setDither(args[0] as Boolean)
    (objVar is Paint && method == "setElegantTextHeight" && args.count() == 1 && args[0] is Boolean) -> objVar.setElegantTextHeight(args[0] as Boolean)
    (objVar is Paint && method == "setFakeBoldText" && args.count() == 1 && args[0] is Boolean) -> objVar.setFakeBoldText(args[0] as Boolean)
    (objVar is Paint && method == "setFilterBitmap" && args.count() == 1 && args[0] is Boolean) -> objVar.setFilterBitmap(args[0] as Boolean)
    (objVar is Paint && method == "setFlags" && args.count() == 1 ) -> objVar.setFlags(anyToInt(args[0]))
    (objVar is Paint && method == "setFontFeatureSettings" && args.count() == 1 && args[0] is String) -> objVar.setFontFeatureSettings(args[0] as String)
    (objVar is Paint && method == "setHinting" && args.count() == 1 ) -> objVar.setHinting(anyToInt(args[0]))
    (objVar is Paint && method == "setLetterSpacing" && args.count() == 1 ) -> objVar.setLetterSpacing(anyToFloat(args[0]))
    (objVar is Paint && method == "setLinearText" && args.count() == 1 && args[0] is Boolean) -> objVar.setLinearText(args[0] as Boolean)
    (objVar is Paint && method == "setMaskFilter" && args.count() == 1 && args[0] is MaskFilter) -> objVar.setMaskFilter(args[0] as MaskFilter)
    (objVar is Paint && method == "setPathEffect" && args.count() == 1 && args[0] is PathEffect) -> objVar.setPathEffect(args[0] as PathEffect)
    (objVar is Paint && method == "setRasterizer" && args.count() == 1 && args[0] is Rasterizer) -> objVar.setRasterizer(args[0] as Rasterizer)
    (objVar is Paint && method == "setShader" && args.count() == 1 && args[0] is Shader) -> objVar.setShader(args[0] as Shader)
    (objVar is Paint && method == "setShadowLayer" && args.count() == 4 ) -> objVar.setShadowLayer(anyToFloat(args[0]), anyToFloat(args[1]), anyToFloat(args[2]), anyToInt(args[3]))
    (objVar is Paint && method == "setStrikeThruText" && args.count() == 1 && args[0] is Boolean) -> objVar.setStrikeThruText(args[0] as Boolean)
    (objVar is Paint && method == "setStrokeCap" && args.count() == 1 && args[0] is Paint.Cap) -> objVar.setStrokeCap(args[0] as Paint.Cap)
    (objVar is Paint && method == "setStrokeJoin" && args.count() == 1 && args[0] is Paint.Join) -> objVar.setStrokeJoin(args[0] as Paint.Join)
    (objVar is Paint && method == "setStrokeMiter" && args.count() == 1 ) -> objVar.setStrokeMiter(anyToFloat(args[0]))
    (objVar is Paint && method == "setStrokeWidth" && args.count() == 1 ) -> objVar.setStrokeWidth(anyToFloat(args[0]))
    (objVar is Paint && method == "setStyle" && args.count() == 1 && args[0] is Paint.Style) -> objVar.setStyle(args[0] as Paint.Style)
    (objVar is Paint && method == "setSubpixelText" && args.count() == 1 && args[0] is Boolean) -> objVar.setSubpixelText(args[0] as Boolean)
    (objVar is Paint && method == "setTextAlign" && args.count() == 1 && args[0] is Paint.Align) -> objVar.setTextAlign(args[0] as Paint.Align)
    (objVar is Paint && method == "setTextLocale" && args.count() == 1 && args[0] is Locale) -> objVar.setTextLocale(args[0] as Locale)
    (objVar is Paint && method == "setTextScaleX" && args.count() == 1 ) -> objVar.setTextScaleX(anyToFloat(args[0]))
    (objVar is Paint && method == "setTextSize" && args.count() == 1 ) -> objVar.setTextSize(anyToFloat(args[0]))
    (objVar is Paint && method == "setTextSkewX" && args.count() == 1 ) -> objVar.setTextSkewX(anyToFloat(args[0]))
    (objVar is Paint && method == "setTypeface" && args.count() == 1 && args[0] is Typeface) -> objVar.setTypeface(args[0] as Typeface)
    (objVar is Paint && method == "setUnderlineText" && args.count() == 1 && args[0] is Boolean) -> objVar.setUnderlineText(args[0] as Boolean)
    (objVar is Paint && method == "setXfermode" && args.count() == 1 && args[0] is Xfermode) -> objVar.setXfermode(args[0] as Xfermode)
    (objVar == "Bitmap\$CompressFormat" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Bitmap.CompressFormat.valueOf(args[0] as String)
    (objVar == "Bitmap\$CompressFormat" && method == "values" && args.count() == 0 ) -> Bitmap.CompressFormat.values()
    (objVar == "Shader\$TileMode" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Shader.TileMode.valueOf(args[0] as String)
    (objVar == "Shader\$TileMode" && method == "values" && args.count() == 0 ) -> Shader.TileMode.values()
    (objVar == "Path\$Direction" && method == "valueOf" && args.count() == 1 && args[0] is String) -> Path.Direction.valueOf(args[0] as String)
    (objVar == "Path\$Direction" && method == "values" && args.count() == 0 ) -> Path.Direction.values()
    (objVar == "RerendererLoader" && method == "bitmapFromUrl" && args.count() == 1 && args[0] is String) -> RerendererLoader.bitmapFromUrl(args[0] as String)
    else -> throw Exception("Can't call ${method}")
}

fun doGet(vars: Map<String, Any?>, objVar: String, attr: String): Any = when {
    (objVar == "Typeface" && attr == "BOLD") -> Typeface.BOLD
    (objVar == "Typeface" && attr == "BOLD_ITALIC") -> Typeface.BOLD_ITALIC
    (objVar == "Typeface" && attr == "ITALIC") -> Typeface.ITALIC
    (objVar == "Typeface" && attr == "NORMAL") -> Typeface.NORMAL
    (objVar == "Canvas" && attr == "ALL_SAVE_FLAG") -> Canvas.ALL_SAVE_FLAG
    (objVar == "Canvas" && attr == "CLIP_SAVE_FLAG") -> Canvas.CLIP_SAVE_FLAG
    (objVar == "Canvas" && attr == "CLIP_TO_LAYER_SAVE_FLAG") -> Canvas.CLIP_TO_LAYER_SAVE_FLAG
    (objVar == "Canvas" && attr == "FULL_COLOR_LAYER_SAVE_FLAG") -> Canvas.FULL_COLOR_LAYER_SAVE_FLAG
    (objVar == "Canvas" && attr == "HAS_ALPHA_LAYER_SAVE_FLAG") -> Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
    (objVar == "Canvas" && attr == "MATRIX_SAVE_FLAG") -> Canvas.MATRIX_SAVE_FLAG
    (objVar == "Bitmap" && attr == "DENSITY_NONE") -> Bitmap.DENSITY_NONE
    (objVar == "Matrix" && attr == "MPERSP_0") -> Matrix.MPERSP_0
    (objVar == "Matrix" && attr == "MPERSP_1") -> Matrix.MPERSP_1
    (objVar == "Matrix" && attr == "MPERSP_2") -> Matrix.MPERSP_2
    (objVar == "Matrix" && attr == "MSCALE_X") -> Matrix.MSCALE_X
    (objVar == "Matrix" && attr == "MSCALE_Y") -> Matrix.MSCALE_Y
    (objVar == "Matrix" && attr == "MSKEW_X") -> Matrix.MSKEW_X
    (objVar == "Matrix" && attr == "MSKEW_Y") -> Matrix.MSKEW_Y
    (objVar == "Matrix" && attr == "MTRANS_X") -> Matrix.MTRANS_X
    (objVar == "Matrix" && attr == "MTRANS_Y") -> Matrix.MTRANS_Y
    (objVar == "Color" && attr == "BLACK") -> Color.BLACK
    (objVar == "Color" && attr == "BLUE") -> Color.BLUE
    (objVar == "Color" && attr == "CYAN") -> Color.CYAN
    (objVar == "Color" && attr == "DKGRAY") -> Color.DKGRAY
    (objVar == "Color" && attr == "GRAY") -> Color.GRAY
    (objVar == "Color" && attr == "GREEN") -> Color.GREEN
    (objVar == "Color" && attr == "LTGRAY") -> Color.LTGRAY
    (objVar == "Color" && attr == "MAGENTA") -> Color.MAGENTA
    (objVar == "Color" && attr == "RED") -> Color.RED
    (objVar == "Color" && attr == "TRANSPARENT") -> Color.TRANSPARENT
    (objVar == "Color" && attr == "WHITE") -> Color.WHITE
    (objVar == "Color" && attr == "YELLOW") -> Color.YELLOW
    (objVar == "Paint" && attr == "ANTI_ALIAS_FLAG") -> Paint.ANTI_ALIAS_FLAG
    (objVar == "Paint" && attr == "DEV_KERN_TEXT_FLAG") -> Paint.DEV_KERN_TEXT_FLAG
    (objVar == "Paint" && attr == "DITHER_FLAG") -> Paint.DITHER_FLAG
    (objVar == "Paint" && attr == "EMBEDDED_BITMAP_TEXT_FLAG") -> Paint.EMBEDDED_BITMAP_TEXT_FLAG
    (objVar == "Paint" && attr == "FAKE_BOLD_TEXT_FLAG") -> Paint.FAKE_BOLD_TEXT_FLAG
    (objVar == "Paint" && attr == "FILTER_BITMAP_FLAG") -> Paint.FILTER_BITMAP_FLAG
    (objVar == "Paint" && attr == "HINTING_OFF") -> Paint.HINTING_OFF
    (objVar == "Paint" && attr == "HINTING_ON") -> Paint.HINTING_ON
    (objVar == "Paint" && attr == "LINEAR_TEXT_FLAG") -> Paint.LINEAR_TEXT_FLAG
    (objVar == "Paint" && attr == "STRIKE_THRU_TEXT_FLAG") -> Paint.STRIKE_THRU_TEXT_FLAG
    (objVar == "Paint" && attr == "SUBPIXEL_TEXT_FLAG") -> Paint.SUBPIXEL_TEXT_FLAG
    (objVar == "Paint" && attr == "UNDERLINE_TEXT_FLAG") -> Paint.UNDERLINE_TEXT_FLAG
    else -> throw Exception("Can't get non-constant ${attr}")
}
