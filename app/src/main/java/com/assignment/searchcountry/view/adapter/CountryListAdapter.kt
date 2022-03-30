package com.assignment.searchcountry.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.assignment.searchcountry.databinding.CustomCountryListBinding
import com.assignment.searchcountry.model.Country

class CountryListAdapter(
    private val countryList: MutableList<Country>,
    private val countryListener: CountryListListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var countryFilterList: MutableList<Country>

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding =
            CustomCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryListAdapterViewHolder(binding)
    }

    init {
        countryFilterList = countryList
    }

    fun getCountryList(): MutableList<Country> {
        return countryList
    }

    fun updateCountryList(countryFilterList: MutableList<Country>) {
        this.countryFilterList = countryFilterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CountryListAdapterViewHolder).bindData(
            countryFilterList[position],
            countryListener
        )
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    class CountryListAdapterViewHolder(private val viewBinding: CustomCountryListBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindData(
            country: Country,
            countryListener: CountryListListener
        ) {
            val countryName = "${country.name}, ${country.country}"
            val lat = "lat : ${country.coord.lat}"
            val long = "lon : " + country.coord.lon.toString()

            viewBinding.apply {
                tvCountry.text = countryName
                tvLat.text = lat
                tvLon.text = long

                root.setOnClickListener {
                    countryListener.onClickItem(country)
                }
            }
        }
    }

    interface CountryListListener {
        fun onClickItem(country: Country)
    }
}