package com.github.dmitrikudrenko.countryphonecodechooser.ui

import android.os.Bundle
import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeAdapter
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeHeaderAdapter
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.OnCodeSelectListener
import com.github.dmitrikudrenko.countryphonecodechooser.utils.AssetsReader
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import java.util.*

private const val CODES_FILE = "country_codes.json"

@RestrictTo(Scope.LIBRARY)
class CountryCodeChooserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryCodeHeaderAdapter

    private val countryList: List<CountryCode>
        get() {
            val codes = AssetsReader.read(context, CODES_FILE, Array<CountryCode>::class.java)
            Collections.sort(codes)
            return codes
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.f_country_code_chooser, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countryList = countryList
        val baseAdapter = CountryCodeAdapter()
        adapter = CountryCodeHeaderAdapter(baseAdapter)
        baseAdapter.update(countryList)
        adapter.setOnCodeSelectListener(object : OnCodeSelectListener {
            override fun onCodeSelected(countryCode: CountryCode) {
                onCountrySelected(countryCode)
            }

        })
        var position = 0
        arguments?.let {
            val countryCode = it.getParcelable<CountryCode>(CountryCodeChooserActivity.EXTRA_COUNTRY)
            position = countryList.indexOf(countryCode)
            adapter.setSelected(countryCode)
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(StickyRecyclerHeadersDecoration(adapter))
        if (position > 0) {
            recyclerView.scrollToPosition(position - 1)
        }
    }

    private fun onCountrySelected(countryCode: CountryCode) {
        val activity = activity
        (activity as? CountryCodeChooserActivity)?.onCountrySelected(countryCode)
    }

    fun onSearchChange(query: String) {
        adapter.setFilter(query)
        recyclerView.scrollToPosition(0)
    }
}
