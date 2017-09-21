package com.github.dmitrikudrenko.countryphonecodechooser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;

public class CountryCodeChooserActivity extends AppCompatActivity {
    public static final String KEY_COUNTRY = "code";
    private static final String TAG_FRAGMENT =
            "com.github.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserFragment";
    private CountryCodeChooserFragment fragment;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_country_code_chooser);
        if (savedInstanceState == null) {
            fragment = new CountryCodeChooserFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, TAG_FRAGMENT)
                    .commitAllowingStateLoss();
        } else {
            fragment = (CountryCodeChooserFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_FRAGMENT);
        }

        final Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
        final EditText searchView = findViewById(R.id.search);
        if (searchView != null) {
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    final String text = editable.toString();
                    final String query = text.trim().length() > 0 ? text : null;
                    onQueryTextChange(query);
                }
            });
            searchView.setOnEditorActionListener((view, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchView.clearFocus();
                    return true;
                }
                return false;
            });
        }
    }

    public void onCountrySelected(final CountryCode countryCode) {
        setResult(RESULT_OK, new Intent().putExtra(KEY_COUNTRY, countryCode));
        finish();
    }

    public void onQueryTextChange(final String query) {
        fragment.onSearchChange(query);
    }
}
