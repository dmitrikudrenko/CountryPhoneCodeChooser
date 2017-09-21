package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter;

import android.support.annotation.NonNull;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;

public interface OnCodeSelectListener {
    void onCodeSelected(@NonNull CountryCode countryCode);
}
