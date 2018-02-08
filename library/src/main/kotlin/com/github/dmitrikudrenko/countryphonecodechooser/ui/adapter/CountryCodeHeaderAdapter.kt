package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter


import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeAdapter.CodeViewHolder
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.Section
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.Section.Companion.EMPTY_SECTION
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter

@RestrictTo(Scope.LIBRARY)
class CountryCodeHeaderAdapter(adapter: CountryCodeAdapter) : CountryCodeSectionAdapter<ViewHolder>(adapter), StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, HeaderAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CodeViewHolder) {
            val code = getSectionCountry(position).code
            adapter.bind(holder, code)
        }
    }

    override fun getHeaderId(position: Int): Long {
        val section = getSectionCountry(position).section
        return if (section !== EMPTY_SECTION) section.id.toLong() else -1L
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SectionViewHolder(layoutInflater.inflate(R.layout.i_section, parent, false))
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SectionViewHolder)?.bind(getSectionCountry(position).section)
    }

    private class SectionViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val section: TextView = itemView.findViewById(R.id.section)

        internal fun bind(section: Section) {
            this.section.text = section.id.toString()
            itemView.visibility = if (section !== EMPTY_SECTION) View.VISIBLE else View.GONE
        }
    }
}
