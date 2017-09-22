package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;

@RestrictTo(Scope.LIBRARY)
public class SectionCountry {
    private final Section section;
    private final CountryCode code;

    SectionCountry(final Section section, final CountryCode code) {
        this.section = section;
        this.code = code;
    }

    public Section getSection() {
        return section;
    }

    public CountryCode getCode() {
        return code;
    }
}
