package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter

import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode

@RestrictTo(Scope.LIBRARY)
interface OnCodeSelectListener {
    fun onCodeSelected(countryCode: CountryCode)
}
