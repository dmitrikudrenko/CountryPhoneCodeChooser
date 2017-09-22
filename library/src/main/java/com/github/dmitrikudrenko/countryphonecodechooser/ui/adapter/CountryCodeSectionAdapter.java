package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.SectionCountry;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.SectionDataWrapper;

import java.util.List;

@RestrictTo(Scope.LIBRARY)
public abstract class CountryCodeSectionAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> implements HeaderAdapter {
    protected final CountryCodeAdapter adapter;
    private final SectionDataWrapper sectionDataWrapper = new SectionDataWrapper();

    public CountryCodeSectionAdapter(CountryCodeAdapter adapter) {
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                List<CountryCode> codes = adapter.getData();
                sectionDataWrapper.onChanged(codes);
            }
        });
    }

    @Override
    public void setOnCodeSelectListener(final OnCodeSelectListener onCodeSelectListener) {
        adapter.setOnCodeSelectListener(onCodeSelectListener);
    }

    @Override
    public void setSelected(final CountryCode selected) {
        adapter.setSelected(selected);
    }

    @Override
    public void setFilter(final String filter) {
        sectionDataWrapper.setFilter(filter);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sectionDataWrapper.getItemCount();
    }

    public SectionCountry getSectionCountry(int position) {
        return sectionDataWrapper.getSectionCountry(position);
    }
}
