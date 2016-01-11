package com.nvbn.tryrerenderer

import kotlin.reflect.*
import android.graphics.*

fun KType.isNumeric() = listOf(
        Number::class.defaultType,
        Double::class.defaultType,
        Float::class.defaultType,
        Long::class.defaultType,
        Int::class.defaultType,
        Short::class.defaultType,
        Byte::class.defaultType).contains(this)

fun KParameter.compatible(arg: Any): Boolean = when {
    type == arg.javaClass.kotlin.defaultType -> true
    type.isNumeric() && arg.javaClass.kotlin.defaultType.isNumeric() -> true
    else -> false
}

/**
 * Returns `true` when function signature is compatible to passed args.
 */
fun KFunction<*>.compatible(args: List<Any>) = when {
    parameters.count() != args.count() -> false
    parameters.zip(args).all {
        val (param, arg) = it
        param.compatible(arg)
    } -> true
    else -> false
}

fun KClass<*>.rNew(args: List<Any>): Any? =
        if (primaryConstructor is KFunction<*> && primaryConstructor!!.compatible(args))
            primaryConstructor!!.call(args)
        else
            constructors.filter({ it.compatible(args) })[0].call(args)

fun KClass<*>.rCall(method: String, args: List<Any>): Any? =
        staticFunctions.filter(
                { it.name == method && it.compatible(args) })[0].call(args)

fun Any.rCall(method: String, args: List<Any>): Any? =
        javaClass.kotlin.declaredFunctions.filter(
                { it.name == method && it.compatible(args) })[0].call(args)

fun KClass<*>.rGet(attr: String): Any? =
        staticProperties.filter({ it.name == attr })[0].get()

/**
 * Generated in browser by for api v15 on https://developer.android.com/intl/ru/reference/android/graphics/package-summary.html:
 * $('.jd-linkcol a').map(function(_, x) {return '"' + x.text.replace('.', '\\$') + '" to ' + x.text + "::class"}).toArray().join(',\n')
 */
val initialPool = mapOf<String, Any?>(
        "SurfaceTexture\$OnFrameAvailableListener" to SurfaceTexture.OnFrameAvailableListener::class,
        "Bitmap" to Bitmap::class,
        "BitmapFactory" to BitmapFactory::class,
        "BitmapFactory\$Options" to BitmapFactory.Options::class,
        "BitmapRegionDecoder" to BitmapRegionDecoder::class,
        "BitmapShader" to BitmapShader::class,
        "BlurMaskFilter" to BlurMaskFilter::class,
        "Camera" to Camera::class,
        "Canvas" to Canvas::class,
        "Color" to Color::class,
        "ColorFilter" to ColorFilter::class,
        "ColorMatrix" to ColorMatrix::class,
        "ColorMatrixColorFilter" to ColorMatrixColorFilter::class,
        "ComposePathEffect" to ComposePathEffect::class,
        "ComposeShader" to ComposeShader::class,
        "CornerPathEffect" to CornerPathEffect::class,
        "DashPathEffect" to DashPathEffect::class,
        "DiscretePathEffect" to DiscretePathEffect::class,
        "DrawFilter" to DrawFilter::class,
        "EmbossMaskFilter" to EmbossMaskFilter::class,
        "ImageFormat" to ImageFormat::class,
        "Interpolator" to Interpolator::class,
        "LightingColorFilter" to LightingColorFilter::class,
        "LinearGradient" to LinearGradient::class,
        "MaskFilter" to MaskFilter::class,
        "Matrix" to Matrix::class,
        "Movie" to Movie::class,
        "NinePatch" to NinePatch::class,
        "Paint" to Paint::class,
        "Paint\$FontMetrics" to Paint.FontMetrics::class,
        "Paint\$FontMetricsInt" to Paint.FontMetricsInt::class,
        "PaintFlagsDrawFilter" to PaintFlagsDrawFilter::class,
        "Path" to Path::class,
        "PathDashPathEffect" to PathDashPathEffect::class,
        "PathEffect" to PathEffect::class,
        "PathMeasure" to PathMeasure::class,
        "Picture" to Picture::class,
        "PixelFormat" to PixelFormat::class,
        "Point" to Point::class,
        "PointF" to PointF::class,
        "PorterDuff" to PorterDuff::class,
        "PorterDuffColorFilter" to PorterDuffColorFilter::class,
        "PorterDuffXfermode" to PorterDuffXfermode::class,
        "RadialGradient" to RadialGradient::class,
        "Rect" to Rect::class,
        "RectF" to RectF::class,
        "Region" to Region::class,
        "RegionIterator" to RegionIterator::class,
        "Shader" to Shader::class,
        "SumPathEffect" to SumPathEffect::class,
        "SurfaceTexture" to SurfaceTexture::class,
        "SweepGradient" to SweepGradient::class,
        "Typeface" to Typeface::class,
        "Xfermode" to Xfermode::class,
        "YuvImage" to YuvImage::class,
        "Bitmap\$CompressFormat" to Bitmap.CompressFormat::class,
        "Bitmap\$Config" to Bitmap.Config::class,
        "BlurMaskFilter\$Blur" to BlurMaskFilter.Blur::class,
        "Canvas\$EdgeType" to Canvas.EdgeType::class,
        "Canvas\$VertexMode" to Canvas.VertexMode::class,
        "Interpolator\$Result" to Interpolator.Result::class,
        "Matrix\$ScaleToFit" to Matrix.ScaleToFit::class,
        "Paint\$Align" to Paint.Align::class,
        "Paint\$Cap" to Paint.Cap::class,
        "Paint\$Join" to Paint.Join::class,
        "Paint\$Style" to Paint.Style::class,
        "Path\$Direction" to Path.Direction::class,
        "Path\$FillType" to Path.FillType::class,
        "PathDashPathEffect\$Style" to PathDashPathEffect.Style::class,
        "PorterDuff\$Mode" to PorterDuff.Mode::class,
        "Region\$Op" to Region.Op::class,
        "Shader\$TileMode" to Shader.TileMode::class)