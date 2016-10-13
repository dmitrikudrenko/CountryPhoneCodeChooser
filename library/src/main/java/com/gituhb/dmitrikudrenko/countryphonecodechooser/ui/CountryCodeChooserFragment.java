package com.gituhb.dmitrikudrenko.countryphonecodechooser.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.utils.AssetsReader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.Collections;
import java.util.List;

public class CountryCodeChooserFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected CountryCodeHeaderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_country_code_chooser, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<CountryCode> countryList = getCountryList();
        adapter = new CountryCodeHeaderAdapter();
        adapter.update(countryList);
        adapter.setOnCodeSelectListener(new CountryCodeHeaderAdapter.OnCodeSelectListener() {
            @Override
            public void onCodeSelected(@NonNull CountryCode countryCode) {
                onCountrySelected(countryCode);
            }
        });
        int position = 0;
        if (getArguments() != null) {
            CountryCode countryCode = getArguments().getParcelable(CountryCodeChooserActivity.KEY_COUNTRY);
            position = countryList.indexOf(countryCode);
            adapter.setSelected(countryCode);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
        if (position > 0)
            recyclerView.scrollToPosition(position - 1);
    }

    private void onCountrySelected(@NonNull CountryCode countryCode) {
        FragmentActivity activity = getActivity();
        if (activity instanceof CountryCodeChooserActivity) {
            ((CountryCodeChooserActivity) activity).onCountrySelected(countryCode);
        }
    }

    private List<CountryCode> getCountryList() {
        List<CountryCode> codes = AssetsReader.read(getContext(), "country_codes.json", CountryCode[].class);
        Collections.sort(codes);
        return codes;
    }

    public void onSearchChange(String query) {
        adapter.setFilter(query);
        recyclerView.scrollToPosition(0);
    }
}
