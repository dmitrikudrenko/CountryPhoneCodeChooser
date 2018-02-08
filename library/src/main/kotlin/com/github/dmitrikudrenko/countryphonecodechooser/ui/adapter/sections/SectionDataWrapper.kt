package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections

import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.HeaderAdapter.Companion.HEADERS
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.Section.Companion.EMPTY_SECTION
import com.github.dmitrikudrenko.countryphonecodechooser.utils.containsIgnoreCase
import java.util.*


class SectionDataWrapper {
    private val sections: Array<Section?> = arrayOfNulls(HEADERS.size + 1)
    private var originalData = arrayListOf<SectionCountry>()
    private var filteredData = arrayListOf<SectionCountry>()
    private var filter: String? = null

    val itemCount: Int
        get() = filteredData.size

    init {
        sections[0] = EMPTY_SECTION
        for (i in 1 until HEADERS.size + 1) {
            sections[i] = Section(HEADERS[i - 1])
        }
    }

    fun onChanged(codes: List<CountryCode>) {
        Collections.sort(codes)
        originalData.clear()
        for (code in codes) {
            val section = findSection(code)
            originalData.add(SectionCountry(section, code))
        }
        setFilter(null)
    }

    fun setFilter(filter: String?) {
        this.filter = filter
        filterData()
    }

    private fun filterData() {
        filteredData.clear()

        for (sectionCountry in originalData) {
            val code = sectionCountry.code
            if (matches(code)) {
                add(sectionCountry)
            }
        }

        filter?.let {
            if (!it.isBlank()) {
                Collections.sort(filteredData, FilterCountryComparator(it))
            }
        }
    }

    private fun add(sectionCountry: SectionCountry) {
        if (filter.isNullOrBlank()) {
            filteredData.add(sectionCountry)
        } else {
            filteredData.add(SectionCountry(EMPTY_SECTION, sectionCountry.code))
        }
    }

    private fun matches(code: CountryCode): Boolean {
        return filter.isNullOrBlank()
                || code.name.containsIgnoreCase(filter)
                || code.phoneCode.containsIgnoreCase(filter)
    }

    private fun findSection(code: CountryCode): Section {
        val codeSection = code.section
        for (section in sections) {
            if (section?.id == codeSection) {
                return section!!
            }
        }
        throw RuntimeException("Unknown section")
    }

    fun getSectionCountry(position: Int): SectionCountry {
        return filteredData[position]
    }
}
