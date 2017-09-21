package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode;
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CodeViewHolder> {
    private List<CountryCode> data = Collections.emptyList();
    private CountryCode selected;
    private OnCodeSelectListener onCodeSelectListener;

    @Override
    public CodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.i_country_code, parent, false);
        final CodeViewHolder holder = new CodeViewHolder(view);
        holder.setOnSelectListener(countryCode -> {
            selected = countryCode;
            if (onCodeSelectListener != null) {
                onCodeSelectListener.onCodeSelected(selected);
            }
            notifyDataSetChanged();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(CodeViewHolder holder, int position) {
        CountryCode countryCode = getItem(position);
        bind(holder, countryCode);
    }

    void bind(CodeViewHolder holder, CountryCode countryCode) {
        holder.bind(countryCode, countryCode.equals(selected));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private CountryCode getItem(int position) {
        return data.get(position);
    }

    void setOnCodeSelectListener(OnCodeSelectListener onCodeSelectListener) {
        this.onCodeSelectListener = onCodeSelectListener;
    }

    void setSelected(CountryCode selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public void update(List<CountryCode> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<CountryCode> getData() {
        return data;
    }

    static class CodeViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView code;

        private CountryCode countryCode;

        CodeViewHolder(final View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(android.R.id.icon);
            this.name = itemView.findViewById(R.id.name);
            this.code = itemView.findViewById(R.id.code);
        }

        void bind(final CountryCode countryCode, final boolean selected) {
            this.countryCode = countryCode;
            final Context context = itemView.getContext();
            final int borderColor = ContextCompat.getColor(context, R.color.border);
            final int borderSize = context.getResources().getInteger(R.integer.border_size);
            Picasso.with(context)
                    .load(getFlagImageResource(context, countryCode))
                    .fit()
                    .transform(new BorderTransformation(borderColor, borderSize))
                    .into(icon);
            this.name.setText(countryCode.getName());
            this.code.setText(countryCode.getPhoneCode());
            this.itemView.setSelected(selected);
        }

        private int getFlagImageResource(final Context context, final CountryCode countryCode) {
            final String resourceName = "country_flag_" + countryCode.getCode();
            return context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        }

        void setOnSelectListener(final OnCodeSelectListener onSelectListener) {
            itemView.setOnClickListener(view -> {
                if (countryCode != null) {
                    onSelectListener.onCodeSelected(countryCode);
                }
            });
        }
    }
}
