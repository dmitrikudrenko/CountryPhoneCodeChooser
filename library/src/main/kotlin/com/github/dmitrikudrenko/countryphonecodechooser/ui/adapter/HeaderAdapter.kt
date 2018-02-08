package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter

import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode

@RestrictTo(Scope.LIBRARY)
interface HeaderAdapter {

    fun setOnCodeSelectListener(onCodeSelectListener: OnCodeSelectListener)

    fun setSelected(selected: CountryCode)

    fun setFilter(filter: String)

    companion object {
        val HEADERS = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
    }
}
