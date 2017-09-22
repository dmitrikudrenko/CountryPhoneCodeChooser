package com.github.dmitrikudrenko.countryphonecodechooser.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;

import java.lang.reflect.Field;

class ToolbarSwitcherBuilder {
    private final Context context;
    private final Toolbar searchToolbar;
    private final OnSearchInputListener onSearchInputListener;

    private Menu searchMenu;
    private MenuItem searchMenuItem;

    private ToolbarSwitcherBuilder(Toolbar searchToolbar, OnSearchInputListener onSearchInputListener) {
        this.context = searchToolbar.getContext();
        this.searchToolbar = searchToolbar;
        this.onSearchInputListener = onSearchInputListener;
    }

    private void build() {
        searchToolbar.inflateMenu(R.menu.search);
        searchMenu = searchToolbar.getMenu();

        searchToolbar.setNavigationOnClickListener(v -> hideSearchToolbar());

        searchMenuItem = searchMenu.findItem(R.id.search);

        searchMenuItem.setOnActionExpandListener(new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                hideSearchToolbar();
                return true;
            }
        });

        initSearchView();
    }

    private void initSearchView() {
        final SearchView searchView = (SearchView) searchMenu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        setupSearchCloseButton(searchView);
        setupSearchEditText(searchView);
        setupQueryListener(searchView);
    }

    private void setupQueryListener(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            void callSearch(String query) {
                onSearchInputListener.onSearch(query);
            }
        });
    }

    private void setupSearchEditText(SearchView searchView) {
        AutoCompleteTextView editText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setHint(context.getString(R.string.cc_search_hint));
        editText.setHintTextColor(ContextCompat.getColor(context, R.color.cc_search_hint));
        editText.setTextColor(ContextCompat.getColor(context, R.color.cc_search_text));

        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
//            mCursorDrawableRes.set(editText, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSearchCloseButton(SearchView searchView) {
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.vec_close_primary));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void circleReveal(Toolbar toolbar, final boolean isShow) {
        int width = toolbar.getWidth();

        int actionButtonMinWidthMaterial = context.getResources()
                .getDimensionPixelSize(R.dimen.abc_action_button_min_width_material);
        width -= actionButtonMinWidthMaterial - actionButtonMinWidthMaterial / 2;
        width -= context.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = toolbar.getHeight() / 2;

        Animator anim;
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, 0, (float) width);
        } else {
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, (float) width, 0);
        }

        anim.setDuration(context.getResources().getInteger(R.integer.cc_search_toolbar_animation_duration));

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        if (isShow) {
            toolbar.setVisibility(View.VISIBLE);
        }

        anim.start();
    }

    void onSearchMenuItemClicked() {
        showSearchToolbar();
        searchMenuItem.expandActionView();
    }

    boolean onBackPressed() {
        if (searchMenuItem.isActionViewExpanded()) {
            hideSearchToolbar();
            searchMenuItem.collapseActionView();
            return true;
        }
        return false;
    }

    private void showSearchToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(searchToolbar, true);
        } else {
            searchToolbar.setVisibility(View.VISIBLE);
        }
    }

    private void hideSearchToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(searchToolbar, false);
        } else {
            searchToolbar.setVisibility(View.INVISIBLE);
        }
    }

    static ToolbarSwitcherBuilder build(Toolbar searchToolbar, OnSearchInputListener onSearchInputListener) {
        ToolbarSwitcherBuilder toolbarSwitcherBuilder = new ToolbarSwitcherBuilder(searchToolbar, onSearchInputListener);
        toolbarSwitcherBuilder.build();
        return toolbarSwitcherBuilder;
    }
}
