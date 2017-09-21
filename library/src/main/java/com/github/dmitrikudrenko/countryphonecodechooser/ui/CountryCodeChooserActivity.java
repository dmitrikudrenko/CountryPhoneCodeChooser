package com.github.dmitrikudrenko.countryphonecodechooser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;

public class CountryCodeChooserActivity extends AppCompatActivity implements OnSearchInputListener {
    public static final String KEY_COUNTRY = "code";
    private static final String TAG_FRAGMENT =
            "com.github.dmitrikudrenko.countryphonecodechooser.ui.CountryCodeChooserFragment";
    private CountryCodeChooserFragment fragment;
    private ToolbarSwitcherBuilder toolbarSwitcherBuilder;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
        Toolbar searchToolbar = findViewById(R.id.search_toolbar);
        toolbarSwitcherBuilder = ToolbarSwitcherBuilder.build(searchToolbar, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            toolbarSwitcherBuilder.onSearchMenuItemClicked();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onCountrySelected(final CountryCode countryCode) {
        setResult(RESULT_OK, new Intent().putExtra(KEY_COUNTRY, countryCode));
        finish();
    }

    @Override
    public void onSearch(String query) {
        fragment.onSearchChange(query);
    }
}
