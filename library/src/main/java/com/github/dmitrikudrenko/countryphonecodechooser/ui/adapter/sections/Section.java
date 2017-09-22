package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.sections;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo(Scope.LIBRARY)
public class Section {
    public static final Section EMPTY_SECTION = new Section(' ');

    private final char id;

    Section(final char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }
}
