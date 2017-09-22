package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;

import java.util.Comparator;

@RestrictTo(Scope.LIBRARY)
public class FilterCountryComparator implements Comparator<SectionCountry> {
    private static final int MAX_PRIORITY = 10;
    private String filter;

    public FilterCountryComparator(final String filter) {
        this.filter = filter;
    }

    @Override
    public int compare(final SectionCountry firstSectionCountry,
                       final SectionCountry secondSectionCountry) {
        final CountryCode firstCode = firstSectionCountry.getCode();
        final CountryCode secondCode = secondSectionCountry.getCode();

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
