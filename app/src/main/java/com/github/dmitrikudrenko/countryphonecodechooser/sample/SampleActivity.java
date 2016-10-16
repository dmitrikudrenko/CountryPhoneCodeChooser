package com.github.dmitrikudrenko.countryphonecodechooser.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gituhb.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserActivity;

public class SampleActivity extends AppCompatActivity {
    private TextView countryView;
    private CountryCode country;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_sample);
        countryView = (TextView) findViewById(R.id.country);
    }

    public void select(View view) {
        Intent intent = new Intent(this, CountryCodeChooserActivity.class).putExtra(CountryCodeChooserActivity.KEY_COUNTRY, country);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                CountryCode country = data.getParcelableExtra(CountryCodeChooserActivity.KEY_COUNTRY);
                setCountry(country);
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private void setCountry(CountryCode country) {
        this.country = country;
        countryView.setText(country.getName());
    }
}
