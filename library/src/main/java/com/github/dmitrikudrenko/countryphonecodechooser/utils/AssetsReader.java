package com.github.dmitrikudrenko.countryphonecodechooser.utils;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestrictTo(Scope.LIBRARY)
public final class AssetsReader {
    private AssetsReader() {
    }

    public static <T> List<T> read(final Context context, final String filename, final Class<T[]> itemClass) {
        final List<T> list = new ArrayList<>();
        try {
            final String data = readAsset(context, filename);
            if (!TextUtils.isEmpty(data)) {
                final Gson gson = new GsonBuilder().create();
                final T[] array = gson.fromJson(data, itemClass);
                return Arrays.asList(array);
            }
        } catch (final IOException e) {
            Log.e("Asset read error", e.getMessage(), e);
        }
        return list;
    }

    private static String readAsset(final Context context, final String filename) throws IOException {
        final StringBuilder result = new StringBuilder();
        final InputStream inputStream = context.getAssets().open(filename);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            final String line = reader.readLine();
            if (line != null) {
                result.append(line);
            } else break;
        }
        return result.toString();
    }
}
