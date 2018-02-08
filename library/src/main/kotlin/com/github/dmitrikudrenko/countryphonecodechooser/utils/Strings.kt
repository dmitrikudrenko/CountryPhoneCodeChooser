package com.github.dmitrikudrenko.countryphonecodechooser.utils

inline fun String.containsIgnoreCase(other: String?) =
        if (other == null) false else toLowerCase().contains(other.toLowerCase())
