package com.github.dmitrikudrenko.countryphonecodechooser.ui

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R

class CountryCodeChooserActivity : AppCompatActivity(), OnSearchInputListener {
    private lateinit var fragment: CountryCodeChooserFragment
    private lateinit var toolbarSwitcherBuilder: ToolbarSwitcherBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_country_code_chooser)
        if (savedInstanceState == null) {
            fragment = CountryCodeChooserFragment()
            fragment.arguments = intent.extras
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, TAG_FRAGMENT)
                    .commitAllowingStateLoss()
        } else {
            fragment = supportFragmentManager
                    .findFragmentByTag(TAG_FRAGMENT) as CountryCodeChooserFragment
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { _ -> onBackPressed() }
        }
        val searchToolbar = findViewById<Toolbar>(R.id.search_toolbar)
        toolbarSwitcherBuilder = ToolbarSwitcherBuilder.build(searchToolbar, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            if (item.itemId == R.id.search) {
                toolbarSwitcherBuilder.onSearchMenuItemClicked()
                true
            } else {
                super.onOptionsItemSelected(item)
            }

    override fun onBackPressed() {
        if (!toolbarSwitcherBuilder.onBackPressed()) {
            super.onBackPressed()
        }
    }

    fun onCountrySelected(countryCode: CountryCode) {
        setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_COUNTRY, countryCode))
        finish()
    }

    override fun onSearch(query: String) {
        fragment.onSearchChange(query)
    }

    companion object {
        val EXTRA_COUNTRY = "key_country"
        private val TAG_FRAGMENT = "CountryCodeChooserFragment"

        fun start(activity: Activity, countryCode: CountryCode?, requestCode: Int) {
            activity.startActivityForResult(intent(activity, countryCode), requestCode)
        }

        fun start(fragment: Fragment, countryCode: CountryCode?, requestCode: Int) {
            fragment.startActivityForResult(intent(fragment.activity, countryCode), requestCode)
        }

        private fun intent(context: Context, countryCode: CountryCode?) =
                Intent(context, CountryCodeChooserActivity::class.java)
                        .putExtra(EXTRA_COUNTRY, countryCode)
    }
}
