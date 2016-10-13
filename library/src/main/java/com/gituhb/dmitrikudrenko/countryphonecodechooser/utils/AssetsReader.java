package com.gituhb.dmitrikudrenko.countryphonecodechooser.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssetsReader {
    public static <T> List<T> read(Context context, String filename, Class<T[]> tClass) {
        List<T> list = new ArrayList<>();
        try {
            String data = readAsset(context, filename);
            if (!TextUtils.isEmpty(data)) {
                Gson gson = new GsonBuilder().create();
                T[] array = gson.fromJson(data, tClass);
                return Arrays.asList(array);
            }
        } catch (IOException e) {
            Log.e("Asset read error", e.getMessage(), e);
        }
        return list;
    }

    private static String readAsset(Context context, String filename) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                result.append(line);
            } else break;
        }
        return result.toString();
    }
}
