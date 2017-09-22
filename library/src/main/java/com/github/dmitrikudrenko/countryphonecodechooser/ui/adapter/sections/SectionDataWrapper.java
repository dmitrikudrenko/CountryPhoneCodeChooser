package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections;

import android.text.TextUtils;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.HeaderAdapter.HEADERS;
import static com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections.Section.EMPTY_SECTION;

public class SectionDataWrapper {
    private final Section[] sections;
    private List<SectionCountry> originalData;
    private List<SectionCountry> filteredData;
    private String filter;

    public SectionDataWrapper() {
        sections = new Section[HEADERS.length + 1];
        sections[0] = EMPTY_SECTION;
        for (int i = 1; i < HEADERS.length + 1; i++) {
            sections[i] = new Section(HEADERS[i - 1]);
        }
    }

    public void onChanged(List<CountryCode> codes) {
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

    public void setFilter(String filter) {
        this.filter = filter;
        filterData();
    }

    private void filterData() {
        if (filteredData == null) {
            filteredData = new ArrayList<>();
        } else {
            filteredData.clear();
        }

        for (final SectionCountry sectionCountry : originalData) {
            final CountryCode code = sectionCountry.getCode();
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
            filteredData.add(new SectionCountry(EMPTY_SECTION, sectionCountry.getCode()));
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
            if (section.getId() == codeSection) {
                return section;
            }
        }
        throw new RuntimeException("Unknown section");
    }

    private boolean isFilterSet() {
        return !TextUtils.isEmpty(filter);
    }

    public int getItemCount() {
        return filteredData.size();
    }

    public SectionCountry getSectionCountry(int position) {
        return filteredData.get(position);
    }
}
