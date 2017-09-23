package com.github.dmitrikudrenko.countryphonecodechooser.utils;

import android.content.Intent;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserActivity;

public class CountryCodeIntent {
    public static CountryCode get(Intent intent) {
        if (intent != null) {
            return intent.getParcelableExtra(CountryCodeChooserActivity.EXTRA_COUNTRY);
        }
        return null;
    }
}
