package com.gituhb.dmitrikudrenko.countryphonecodechooser.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.model.CountryCode;

public class CountryCodeChooserActivity extends AppCompatActivity {
    public static final String KEY_COUNTRY = "code";
    protected CountryCodeChooserFragment fragment;
    protected EditText searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_country_code_chooser);
        if (savedInstanceState == null) {
            fragment = new CountryCodeChooserFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, CountryCodeChooserFragment.class.getName())
                    .commitAllowingStateLoss();
        } else fragment = (CountryCodeChooserFragment) getSupportFragmentManager()
                .findFragmentByTag(CountryCodeChooserFragment.class.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        searchView = (EditText) findViewById(R.id.search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                String query = s.trim().length() > 0 ? s : null;
                onQueryTextChange(query);
            }
        });
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchView.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    public void onCountrySelected(@NonNull CountryCode countryCode) {
        Toast.makeText(this, "Country selected - " + countryCode.getName(), Toast.LENGTH_LONG).show();
    }

    public boolean onQueryTextChange(String query) {
        fragment.onSearchChange(query);
        return false;
    }
}
