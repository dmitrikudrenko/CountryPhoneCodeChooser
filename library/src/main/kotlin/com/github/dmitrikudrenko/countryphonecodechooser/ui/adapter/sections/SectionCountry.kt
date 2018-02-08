package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections

import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode

@RestrictTo(Scope.LIBRARY)
data class SectionCountry internal constructor(val section: Section, val code: CountryCode)
