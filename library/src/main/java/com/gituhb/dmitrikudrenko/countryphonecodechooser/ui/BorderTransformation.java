package com.gituhb.dmitrikudrenko.countryphonecodechooser.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;

import com.squareup.picasso.Transformation;

public class BorderTransformation implements Transformation {
    private int color;

    public BorderTransformation(int color) {
        this.color = color;
    }

    @Override
    public Bitmap transform(@Nullable Bitmap source) {
        if (source == null) return null;

        int borderSize = 2;
        Bitmap output = Bitmap.createBitmap(source.getWidth() + borderSize * 2,
                source.getHeight() + borderSize * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawColor(color);
        canvas.drawBitmap(source, borderSize, borderSize, null);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return BorderTransformation.class.getName();
    }
}
