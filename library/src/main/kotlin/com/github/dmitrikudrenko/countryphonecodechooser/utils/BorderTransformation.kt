package com.github.dmitrikudrenko.countryphonecodechooser.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import com.squareup.picasso.Transformation

@RestrictTo(Scope.LIBRARY)
class BorderTransformation(private val color: Int, private val borderSize: Int) : Transformation {
    private val key = "BorderTransformation, color:$color,borderSize:$borderSize"

    override fun transform(source: Bitmap?): Bitmap? {
        if (source == null) return null

        val borderPadding = borderSize * 2
        val width = source.width + borderPadding
        val height = source.height + borderPadding
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawColor(color)
        canvas.drawBitmap(source, borderSize.toFloat(), borderSize.toFloat(), null)
        source.recycle()
        return output
    }

    override fun key(): String {
        return key
    }
}
