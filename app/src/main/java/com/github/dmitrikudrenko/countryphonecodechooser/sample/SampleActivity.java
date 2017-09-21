package com.github.dmitrikudrenko.countryphonecodechooser.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserActivity;

public class SampleActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private TextView countryView;
    private CountryCode country;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_sample);
        countryView = findViewById(R.id.country);
    }

    public void select(final View view) {
        Intent intent = new Intent(this, CountryCodeChooserActivity.class)
                .putExtra(CountryCodeChooserActivity.KEY_COUNTRY, country);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final CountryCode country = data.getParcelableExtra(CountryCodeChooserActivity.KEY_COUNTRY);
                setCountry(country);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setCountry(final CountryCode country) {
        this.country = country;
        countryView.setText(country.getName());
    }
}
