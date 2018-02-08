package com.github.dmitrikudrenko.countryphonecodechooser.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R

internal class ToolbarSwitcherBuilder private constructor(
        private val searchToolbar: Toolbar,
        private val onSearchInputListener: OnSearchInputListener
) {
    private val context: Context = searchToolbar.context

    private lateinit var searchMenu: Menu
    private lateinit var searchMenuItem: MenuItem

    private fun build() {
        searchToolbar.inflateMenu(R.menu.search)
        searchMenu = searchToolbar.menu

        searchToolbar.setNavigationOnClickListener { _ -> hideSearchToolbar() }

        searchMenuItem = searchMenu.findItem(R.id.search)

        searchMenuItem.setOnActionExpandListener(object : OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem) = true

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                hideSearchToolbar()
                return true
            }
        })

        initSearchView()
    }

    private fun initSearchView() {
        val searchView = searchMenu.findItem(R.id.search).actionView as SearchView
        searchView
                .apply {
                    isSubmitButtonEnabled = false
                    maxWidth = Integer.MAX_VALUE
                }
                .also {
                    setupSearchCloseButton(it)
                    setupSearchEditText(it)
                    setupQueryListener(it)
                }
    }

    private fun setupQueryListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return true
            }

            internal fun callSearch(query: String) {
                onSearchInputListener.onSearch(query)
            }
        })
    }

    private fun setupSearchEditText(searchView: SearchView) {
        val editTextId = android.support.v7.appcompat.R.id.search_src_text
        searchView.findViewById<AutoCompleteTextView>(editTextId).apply {
            hint = context.getString(R.string.cc_search_hint)
            setHintTextColor(ContextCompat.getColor(context, R.color.cc_search_hint))
            setTextColor(ContextCompat.getColor(context, R.color.cc_search_text))
        }
    }

    private fun setupSearchCloseButton(searchView: SearchView) {
        searchView.findViewById<ImageView>(R.id.search_close_btn).apply {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.vec_close_primary))
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun circleReveal(toolbar: Toolbar, isShow: Boolean) {
        var width = toolbar.width

        val actionButtonMinWidthMaterial = context.resources
                .getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)
        width -= actionButtonMinWidthMaterial - actionButtonMinWidthMaterial / 2
        width -= context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)

        val cx = width
        val cy = toolbar.height / 2

        val anim: Animator
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, 0f, width.toFloat())
        } else {
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, width.toFloat(), 0f)
        }

        anim.duration = context.resources.getInteger(R.integer.cc_search_toolbar_animation_duration).toLong()

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                    toolbar.visibility = View.INVISIBLE
                }
            }
        })

        if (isShow) {
            toolbar.visibility = View.VISIBLE
        }

        anim.start()
    }

    fun onSearchMenuItemClicked() {
        showSearchToolbar()
        searchMenuItem.expandActionView()
    }

    fun onBackPressed(): Boolean {
        if (searchMenuItem.isActionViewExpanded) {
            hideSearchToolbar()
            searchMenuItem.collapseActionView()
            return true
        }
        return false
    }

    private fun showSearchToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(searchToolbar, true)
        } else {
            searchToolbar.visibility = View.VISIBLE
        }
    }

    private fun hideSearchToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(searchToolbar, false)
        } else {
            searchToolbar.visibility = View.INVISIBLE
        }
    }

    companion object {

        fun build(searchToolbar: Toolbar, onSearchInputListener: OnSearchInputListener): ToolbarSwitcherBuilder {
            val toolbarSwitcherBuilder = ToolbarSwitcherBuilder(searchToolbar, onSearchInputListener)
            toolbarSwitcherBuilder.build()
            return toolbarSwitcherBuilder
        }
    }
}
