package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter;


import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeAdapter.CodeViewHolder;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.Section;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import static com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.Section.EMPTY_SECTION;

@RestrictTo(Scope.LIBRARY)
public class CountryCodeHeaderAdapter extends CountryCodeSectionAdapter<ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, HeaderAdapter {

    public CountryCodeHeaderAdapter(CountryCodeAdapter adapter) {
        super(adapter);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CodeViewHolder) {
            final CountryCode code = getSectionCountry(position).getCode();
            adapter.bind((CountryCodeAdapter.CodeViewHolder) holder, code);
        }
    }

    @Override
    public long getHeaderId(final int position) {
        final Section section = getSectionCountry(position).getSection();
        return section != EMPTY_SECTION ? section.getId() : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(final ViewGroup parent) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SectionViewHolder(layoutInflater.inflate(R.layout.i_section, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SectionViewHolder) {
            ((SectionViewHolder) holder).bind(getSectionCountry(position).getSection());
        }
    }

    private static class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView section;

        SectionViewHolder(final View itemView) {
            super(itemView);
            this.section = itemView.findViewById(R.id.section);
        }

        void bind(final Section section) {
            this.section.setText(String.valueOf(section.getId()));
            itemView.setVisibility(section != EMPTY_SECTION ? View.VISIBLE : View.GONE);
        }
    }
}
