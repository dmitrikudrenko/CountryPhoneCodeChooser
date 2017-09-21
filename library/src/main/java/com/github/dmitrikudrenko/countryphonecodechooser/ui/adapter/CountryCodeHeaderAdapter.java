package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter;


import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeAdapter.CodeViewHolder;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestrictTo(Scope.LIBRARY)
public class CountryCodeHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private static final char[] HEADERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final Section EMPTY_SECTION = new Section(' ');

    private CountryCodeAdapter adapter;

    private final Section[] sections;
    private List<SectionCountry> originalData;
    private List<SectionCountry> filteredData;
    private String filter;

    public CountryCodeHeaderAdapter(CountryCodeAdapter adapter) {
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                List<CountryCode> codes = adapter.getData();
                Collections.sort(codes);
                if (originalData == null) {
                    originalData = new ArrayList<>(codes.size());
                } else {
                    originalData.clear();
                }
                for (final CountryCode code : codes) {
                    final Section section = findSection(code);
                    originalData.add(new SectionCountry(section, code));
                }
                setFilter(null);
            }
        });
        sections = new Section[HEADERS.length + 1];
        sections[0] = EMPTY_SECTION;
        for (int i = 1; i < HEADERS.length + 1; i++) {
            sections[i] = new Section(HEADERS[i - 1]);
        }
    }

    public void setOnCodeSelectListener(final OnCodeSelectListener onCodeSelectListener) {
        adapter.setOnCodeSelectListener(onCodeSelectListener);
    }

    public void setSelected(final CountryCode selected) {
        adapter.setSelected(selected);
    }

    public void setFilter(final String filter) {
        this.filter = filter;
        filterData();
        notifyDataSetChanged();
    }

    private void filterData() {
        if (filteredData == null) {
            filteredData = new ArrayList<>();
        } else {
            filteredData.clear();
        }

        for (final SectionCountry sectionCountry : originalData) {
            final CountryCode code = sectionCountry.code;
            if (matches(code)) {
                add(sectionCountry);
            }
        }

        if (isFilterSet()) {
            Collections.sort(filteredData, new FilterCountryComparator(filter));
        }
    }

    private void add(final SectionCountry sectionCountry) {
        if (!isFilterSet()) {
            filteredData.add(sectionCountry);
        } else {
            filteredData.add(new SectionCountry(EMPTY_SECTION, sectionCountry.code));
        }
    }

    private boolean matches(final CountryCode code) {
        return !isFilterSet()
                || code.getName().toLowerCase().contains(filter.toLowerCase())
                || code.getPhoneCode().contains(filter);
    }

    private Section findSection(final CountryCode code) {
        final char codeSection = code.getSection();
        for (final Section section : sections) {
            if (section.id == codeSection) {
                return section;
            }
        }
        throw new RuntimeException("Unknown section");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CodeViewHolder) {
            final CountryCode code = filteredData.get(position).code;
            adapter.bind((CountryCodeAdapter.CodeViewHolder) holder, code);
        }
    }

    @Override
    public long getHeaderId(final int position) {
        final Section section = filteredData.get(position).section;
        return section != EMPTY_SECTION ? section.id : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(final ViewGroup parent) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SectionViewHolder(layoutInflater.inflate(R.layout.i_section, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SectionViewHolder) {
            ((SectionViewHolder) holder).bind(filteredData.get(position).section);
        }
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    private boolean isFilterSet() {
        return !TextUtils.isEmpty(filter);
    }

    private static class Section {
        private final char id;

        Section(final char id) {
            this.id = id;
        }
    }

    private static class SectionCountry {
        private final Section section;
        private final CountryCode code;

        SectionCountry(final Section section, final CountryCode code) {
            this.section = section;
            this.code = code;
        }
    }

    private static class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView section;

        SectionViewHolder(final View itemView) {
            super(itemView);
            this.section = itemView.findViewById(R.id.section);
        }

        void bind(final Section section) {
            this.section.setText(String.valueOf(section.id));
            itemView.setVisibility(section != EMPTY_SECTION ? View.VISIBLE : View.GONE);
        }
    }

    private static class FilterCountryComparator implements Comparator<SectionCountry> {
        private static final int MAX_PRIORITY = 10;
        private String filter;

        FilterCountryComparator(final String filter) {
            this.filter = filter;
        }

        @Override
        public int compare(final SectionCountry firstSectionCountry,
                           final SectionCountry secondSectionCountry) {
            final CountryCode firstCode = firstSectionCountry.code;
            final CountryCode secondCode = secondSectionCountry.code;

            final int firstCodePriority = getEntryPriority(firstCode.getName());
            final int secondCodePriority = getEntryPriority(secondCode.getName());
            if (firstCodePriority > secondCodePriority) {
                return -1;
            } else if (secondCodePriority > firstCodePriority) {
                return 1;
            } else {
                return firstCode.compareTo(secondCode);
            }
        }

        private int getEntryPriority(final String value) {
            final String[] words = value.split(" ");
            for (int i = 0; i < words.length; i++) {
                final String word = words[i];
                if (word.toLowerCase().startsWith(filter.toLowerCase())) {
                    return MAX_PRIORITY - i;
                }
            }
            return 1;
        }
    }
}
