package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections

import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope

@RestrictTo(Scope.LIBRARY)
data class Section internal constructor(val id: Char) {
    companion object {
        val EMPTY_SECTION = Section(' ')
    }
}
