package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;

@RestrictTo(Scope.LIBRARY)
public interface HeaderAdapter {
    char[] HEADERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    void setOnCodeSelectListener(final OnCodeSelectListener onCodeSelectListener);

    void setSelected(final CountryCode selected);

    void setFilter(final String filter);
}
