package com.github.dmitrikudrenko.countryphonecodechooser.utils

import android.content.Context
import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import android.util.Log
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

@RestrictTo(Scope.LIBRARY)
object AssetsReader {

    fun <T> read(context: Context?, filename: String, itemClass: Class<Array<T>>): List<T> {
        try {
            val data = readAsset(context, filename)
            if (!data.isEmpty()) {
                val gson = GsonBuilder().create()
                val array = gson.fromJson(data, itemClass)
                return Arrays.asList(*array)
            }
        } catch (e: IOException) {
            Log.e("Asset read error", e.message, e)
        }

        return ArrayList<T>()
    }

    @Throws(IOException::class)
    private fun readAsset(context: Context?, filename: String): String {
        val result = StringBuilder()
        val inputStream = context?.assets?.open(filename)
        val reader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val line = reader.readLine()
            if (line != null) result.append(line) else break
        }
        return result.toString()
    }
}
