package com.github.dmitrikudrenko.countryphonecodechooser.utils

import android.content.Intent
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.github.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserActivity

object CountryCodeIntent {
    operator fun get(intent: Intent?) =
            intent?.getParcelableExtra<CountryCode>(CountryCodeChooserActivity.EXTRA_COUNTRY)
}
