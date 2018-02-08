package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections

import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import java.util.*

private const val MAX_PRIORITY = 10

@RestrictTo(Scope.LIBRARY)
class FilterCountryComparator internal constructor(private val filter: String)
    : Comparator<SectionCountry> {

    override fun compare(firstSectionCountry: SectionCountry,
                         secondSectionCountry: SectionCountry): Int {
        val firstCode = firstSectionCountry.code
        val secondCode = secondSectionCountry.code

        val firstCodePriority = firstCode.name.let { getEntryPriority(it) }
        val secondCodePriority = secondCode.name.let { getEntryPriority(it) }

        return when {
            firstCodePriority > secondCodePriority -> -1
            secondCodePriority > firstCodePriority -> 1
            else -> firstCode.compareTo(secondCode)
        }
    }

    private fun getEntryPriority(value: String): Int {
        val words = value.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for ((i, word) in words.withIndex()) {
            if (word.toLowerCase().startsWith(filter.toLowerCase())) {
                return MAX_PRIORITY - i
            }
        }
        return 1
    }
}
