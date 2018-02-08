package com.github.dmitrikudrenko.countryphonecodechooser.ui.adapter

import android.content.Context
import android.support.annotation.RestrictTo
import android.support.annotation.RestrictTo.Scope
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.dmitrikudrenko.countryphonecodechooser.model.CountryCode
import com.github.dmitrikudrenko.countryphonecodechooser.utils.BorderTransformation
import com.gituhb.dmitrikudrenko.countryphonecodechooser.R
import com.squareup.picasso.Picasso

@RestrictTo(Scope.LIBRARY)
class CountryCodeAdapter : RecyclerView.Adapter<CountryCodeAdapter.CodeViewHolder>() {
    var data = emptyList<CountryCode>()
    private var selected: CountryCode? = null
    private var onCodeSelectListener: OnCodeSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.i_country_code, parent, false)
        val holder = CodeViewHolder(view)
        holder.setOnSelectListener(object : OnCodeSelectListener {
            override fun onCodeSelected(countryCode: CountryCode) {
                selected = countryCode
                if (onCodeSelectListener != null) {
                    onCodeSelectListener!!.onCodeSelected(selected!!)
                }
                notifyDataSetChanged()
            }

        })
        return holder
    }

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        val countryCode = getItem(position)
        bind(holder, countryCode)
    }

    internal fun bind(holder: CodeViewHolder, countryCode: CountryCode) {
        holder.bind(countryCode, countryCode == selected)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getItem(position: Int): CountryCode {
        return data[position]
    }

    internal fun setOnCodeSelectListener(onCodeSelectListener: OnCodeSelectListener) {
        this.onCodeSelectListener = onCodeSelectListener
    }

    internal fun setSelected(selected: CountryCode) {
        this.selected = selected
        notifyDataSetChanged()
    }

    fun update(data: List<CountryCode>) {
        this.data = data
        notifyDataSetChanged()
    }

    class CodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(android.R.id.icon)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val code: TextView = itemView.findViewById(R.id.code)

        private var countryCode: CountryCode? = null

        fun bind(countryCode: CountryCode, selected: Boolean) {
            this.countryCode = countryCode
            val context = itemView.context
            val borderColor = ContextCompat.getColor(context, R.color.cc_border)
            val borderSize = context.resources.getInteger(R.integer.cc_border_size)
            Picasso.with(context)
                    .load(getFlagImageResource(context, countryCode))
                    .fit()
                    .transform(BorderTransformation(borderColor, borderSize))
                    .into(icon)
            this.name.text = countryCode.name
            this.code.text = countryCode.phoneCode
            this.itemView.isSelected = selected
        }

        private fun getFlagImageResource(context: Context, countryCode: CountryCode): Int {
            val code = countryCode.code.toLowerCase()
            val resourceName = "country_flag_$code"
            return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        }

        fun setOnSelectListener(onSelectListener: OnCodeSelectListener) {
            itemView.setOnClickListener { _ ->
                countryCode?.let { onSelectListener.onCodeSelected(it) }
            }
        }
    }
}
