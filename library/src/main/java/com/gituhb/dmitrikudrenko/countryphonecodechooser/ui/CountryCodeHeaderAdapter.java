package com.gituhb.dmitrikudrenko.countryphonecodechooser.ui;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryCodeHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private static final char[] HEADERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final Section EMPTY_SECTION = new Section(' ');

    private final Section[] sections;
    private List<SectionCountry> originalData;
    private List<SectionCountry> filteredData;
    private CountryCode selected;
    private String filter;
    private OnCodeSelectListener onCodeSelectListener;

    public CountryCodeHeaderAdapter() {
        sections = new Section[HEADERS.length + 1];
        sections[0] = EMPTY_SECTION;
        for (int i = 1; i < HEADERS.length + 1; i++) {
            sections[i] = new Section(HEADERS[i - 1]);
        }
    }

    public void setOnCodeSelectListener(OnCodeSelectListener onCodeSelectListener) {
        this.onCodeSelectListener = onCodeSelectListener;
    }

    public void setSelected(CountryCode selected) {
        this.selected = selected;
    }

    public void update(List<CountryCode> codes) {
        Collections.sort(codes);
        if (originalData == null) {
            originalData = new ArrayList<>(codes.size());
        } else originalData.clear();
        for (CountryCode code : codes) {
            Section section = findSection(code);
            originalData.add(new SectionCountry(section, code));
        }
        setFilter(null);
    }

    public void setFilter(String filter) {
        this.filter = filter;
        filterData();
        notifyDataSetChanged();
    }

    private void filterData() {
        if (filteredData == null) {
            filteredData = new ArrayList<>();
        } else filteredData.clear();

        for (SectionCountry sectionCountry : originalData) {
            CountryCode code = sectionCountry.code;
            if (matches(code)) {
                add(sectionCountry);
            }
        }

        if (filter != null) {
            Collections.sort(filteredData, new FilterCountryComparator(filter));
        }
    }

    private void add(SectionCountry sectionCountry) {
        if (filter == null) filteredData.add(sectionCountry);
        else filteredData.add(new SectionCountry(EMPTY_SECTION, sectionCountry.code));
    }

    private boolean matches(CountryCode code) {
        if (filter == null) return true;
        return code.getName().toLowerCase().contains(filter.toLowerCase()) ||
                code.getPhoneCode().contains(filter);
    }

    private Section findSection(CountryCode code) {
        char codeSection = code.getSection();
        for (Section section : sections) {
            if (section.id == codeSection) {
                return section;
            }
        }
        throw new RuntimeException("Unknown section");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CodeViewHolder holder = new CodeViewHolder(layoutInflater.inflate(R.layout.v_country_code_item, parent, false));
        holder.setOnSelectListener(new OnCodeSelectListener() {
            @Override
            public void onCodeSelected(@NonNull CountryCode countryCode) {
                selected = countryCode;
                if (onCodeSelectListener != null) {
                    onCodeSelectListener.onCodeSelected(selected);
                }
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CodeViewHolder) {
            CountryCode code = filteredData.get(position).code;
            ((CodeViewHolder) holder).bind(code, code.equals(selected));
        }
    }

    @Override
    public long getHeaderId(int position) {
        Section section = filteredData.get(position).section;
        return section != EMPTY_SECTION ? section.id : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SectionViewHolder(layoutInflater.inflate(R.layout.v_section_item, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SectionViewHolder) {
            ((SectionViewHolder) holder).bind(filteredData.get(position).section);
        }
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    private static class Section {
        private char id;

        Section(char id) {
            this.id = id;
        }
    }

    private static class SectionCountry {
        private Section section;
        private CountryCode code;

        SectionCountry(Section section, CountryCode code) {
            this.section = section;
            this.code = code;
        }
    }

    private static class CodeViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView code;
        private ImageView chooserStatus;

        private CountryCode countryCode;

        CodeViewHolder(View itemView) {
            super(itemView);
            this.icon = (ImageView) itemView.findViewById(android.R.id.icon);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.code = (TextView) itemView.findViewById(R.id.code);
            this.chooserStatus = (ImageView) itemView.findViewById(R.id.chooser_status);
        }

        void bind(CountryCode countryCode, boolean selected) {
            this.countryCode = countryCode;
            Picasso.with(itemView.getContext())
                    .load(getFlagImageResource(itemView.getContext(), countryCode))
                    .fit()
                    .transform(new BorderTransformation(Color.BLACK))
                    .into(icon);
            this.name.setText(countryCode.getName());
            this.code.setText(countryCode.getPhoneCode());
            this.chooserStatus.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
        }

        private int getFlagImageResource(Context context, CountryCode countryCode) {
            return context.getResources().getIdentifier("country_flag_" + countryCode.getCode(), "drawable", context.getPackageName());
        }

        void setOnSelectListener(final OnCodeSelectListener onSelectListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (countryCode != null)
                        onSelectListener.onCodeSelected(countryCode);
                }
            });
        }
    }

    private static class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView section;

        SectionViewHolder(View itemView) {
            super(itemView);
            this.section = (TextView) itemView.findViewById(R.id.section);
        }

        void bind(Section section) {
            this.section.setText(String.valueOf(section.id));
            itemView.setVisibility(section.id != ' ' ? View.VISIBLE : View.GONE);
        }
    }

    public interface OnCodeSelectListener {
        void onCodeSelected(@NonNull CountryCode countryCode);
    }

    private static class FilterCountryComparator implements Comparator<SectionCountry> {
        private static final int MAX_PRIORITY = 10;
        private String filter;

        FilterCountryComparator(String filter) {
            this.filter = filter;
        }

        @Override
        public int compare(SectionCountry firstSectionCountry, SectionCountry secondSectionCountry) {
            CountryCode firstCode = firstSectionCountry.code;
            CountryCode secondCode = secondSectionCountry.code;

            int firstCodePriority = getEntryPriority(firstCode.getName());
            int secondCodePriority = getEntryPriority(secondCode.getName());
            if (firstCodePriority > secondCodePriority) return -1;
            else if (secondCodePriority > firstCodePriority) return 1;
            else return firstCode.compareTo(secondCode);
        }

        private int getEntryPriority(String value) {
            String[] words = value.split(" ");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.toLowerCase().startsWith(filter.toLowerCase())) {
                    return MAX_PRIORITY - i;
                }
            }
            return 1;
        }
    }
}
