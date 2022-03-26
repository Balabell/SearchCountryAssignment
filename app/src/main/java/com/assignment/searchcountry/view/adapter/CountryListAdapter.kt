package com.assignment.searchcountry.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.assignment.searchcountry.databinding.CustomCountryListBinding
import com.assignment.searchcountry.model.Country

class CountryListAdapter(private var countryList: ArrayList<Country>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var countryFilterList = ArrayList<Country>()

    lateinit var context: Context
    // parent.context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            CustomCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val sch = CountryListAdapterViewHolder(binding)
        context = parent.context
        return sch
    }

    init {
        countryFilterList = countryList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val countryHolder = holder as CountryListAdapterViewHolder

        val countryName =
            countryFilterList[position].name + "," + countryFilterList[position].country

        countryHolder.viewBinding.tvCountry.text = countryName
        countryHolder.viewBinding.tvLat.text = countryFilterList[position].coord.lat.toString()
        countryHolder.viewBinding.tvLon.text = countryFilterList[position].coord.lon.toString()


        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    class CountryListAdapterViewHolder(var viewBinding: CustomCountryListBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

}