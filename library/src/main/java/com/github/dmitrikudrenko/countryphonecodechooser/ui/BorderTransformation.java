package com.github.dmitrikudrenko.countryphonecodechooser.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.squareup.picasso.Transformation;

@RestrictTo(Scope.LIBRARY)
class BorderTransformation implements Transformation {
    private final int color;
    private final int borderSize;

    BorderTransformation(int color, int borderSize) {
        this.color = color;
        this.borderSize = borderSize;
    }

    @Override
    public Bitmap transform(@Nullable Bitmap source) {
        if (source == null) return null;

        final int borderPadding = borderSize * 2;
        final int width = source.getWidth() + borderPadding;
        final int height = source.getHeight() + borderPadding;
        final Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        canvas.drawColor(color);
        canvas.drawBitmap(source, borderSize, borderSize, null);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "BorderTransformation";
    }
}
