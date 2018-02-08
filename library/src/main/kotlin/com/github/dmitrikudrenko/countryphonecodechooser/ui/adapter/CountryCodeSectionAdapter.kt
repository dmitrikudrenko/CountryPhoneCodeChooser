package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter

import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.AdapterDataObserver
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.SectionCountry
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.SectionDataWrapper

@RestrictTo(Scope.LIBRARY)
abstract class CountryCodeSectionAdapter<T : RecyclerView.ViewHolder>
internal constructor(protected val adapter: CountryCodeAdapter)
    : RecyclerView.Adapter<T>(), HeaderAdapter {
    private val sectionDataWrapper = SectionDataWrapper()

    init {
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                val codes = adapter.data
                sectionDataWrapper.onChanged(codes)
            }
        })
    }

    override fun setOnCodeSelectListener(onCodeSelectListener: OnCodeSelectListener) {
        adapter.setOnCodeSelectListener(onCodeSelectListener)
    }

    override fun setSelected(selected: CountryCode) {
        adapter.setSelected(selected)
    }

    override fun setFilter(filter: String) {
        sectionDataWrapper.setFilter(filter)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return sectionDataWrapper.itemCount
    }

    internal fun getSectionCountry(position: Int): SectionCountry {
        return sectionDataWrapper.getSectionCountry(position)
    }
}
