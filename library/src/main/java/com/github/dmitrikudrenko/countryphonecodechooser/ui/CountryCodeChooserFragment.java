package com.github.dmitrikudrenko.countryphonecodechooser.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeAdapter;
import com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter.CountryCodeHeaderAdapter;
import com.github.dmitrikudrenko.countryphonecodechooser.utils.AssetsReader;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.Collections;
import java.util.List;

@RestrictTo(Scope.LIBRARY)
public class CountryCodeChooserFragment extends Fragment {
    private static final String CODES_FILE = "country_codes.json";
    private RecyclerView recyclerView;
    private CountryCodeHeaderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final @Nullable ViewGroup container,
                             final @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_country_code_chooser, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<CountryCode> countryList = getCountryList();
        CountryCodeAdapter baseAdapter = new CountryCodeAdapter();
        this.adapter = new CountryCodeHeaderAdapter(baseAdapter);
        baseAdapter.update(countryList);
        this.adapter.setOnCodeSelectListener(this::onCountrySelected);
        int position = 0;
        if (getArguments() != null) {
            CountryCode countryCode = getArguments().getParcelable(CountryCodeChooserActivity.KEY_COUNTRY);
            position = countryList.indexOf(countryCode);
            this.adapter.setSelected(countryCode);
        }
        recyclerView.setAdapter(this.adapter);
        recyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(this.adapter));
        if (position > 0) {
            recyclerView.scrollToPosition(position - 1);
        }
    }

    private void onCountrySelected(@NonNull CountryCode countryCode) {
        FragmentActivity activity = getActivity();
        if (activity instanceof CountryCodeChooserActivity) {
            ((CountryCodeChooserActivity) activity).onCountrySelected(countryCode);
        }
    }

    private List<CountryCode> getCountryList() {
        final List<CountryCode> codes = AssetsReader.read(getContext(), CODES_FILE, CountryCode[].class);
        Collections.sort(codes);
        return codes;
    }

    public void onSearchChange(String query) {
        adapter.setFilter(query);
        recyclerView.scrollToPosition(0);
    }
}
