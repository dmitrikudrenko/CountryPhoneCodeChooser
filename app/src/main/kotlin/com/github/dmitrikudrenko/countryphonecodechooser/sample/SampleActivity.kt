package com.github.dmitrikudrenko.countryphonecodechooser.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.github.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserActivity
import com.github.dmitrikudrenko.countryphonecodechooser.utils.CountryCodeIntent

private const val REQUEST_CODE = 1

class SampleActivity : AppCompatActivity() {
    private lateinit var countryView: TextView
    private var country: CountryCode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_sample)
        countryView = findViewById(R.id.country)
        findViewById<View>(R.id.selectBtn).setOnClickListener { select() }
    }

    private fun select() {
        CountryCodeChooserActivity.start(this, country, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    setCountry(CountryCodeIntent[data])
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setCountry(country: CountryCode?) {
        country?.let {
            this.country = it
            countryView.text = it.name
        }
    }
}
