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
    private val countryList: ArrayList<Country>,
    private val countryListener: CountryListListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var countryFilterList = ArrayList<Country>()

    lateinit var context: Context
    // parent.context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding =
            CustomCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryListAdapterViewHolder(binding)
    }

    // size = 209557
    init {
        countryFilterList = countryList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val countryHolder = holder as CountryListAdapterViewHolder

        val countryName =
            countryFilterList[position].name + ", " + countryFilterList[position].country

        countryHolder.viewBinding.tvCountry.text = countryName
        countryHolder.viewBinding.tvLat.text = countryFilterList[position].coord.lat.toString()
        countryHolder.viewBinding.tvLon.text = countryFilterList[position].coord.lon.toString()


        holder.itemView.setOnClickListener {
            countryListener.onClickItem(countryFilterList[position])
        }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    class CountryListAdapterViewHolder(val viewBinding: CustomCountryListBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint.isNullOrEmpty()) {
                    filterResults.apply {
                        values = countryList
                        count = countryList.size
                    }
                } else {
                    val filterList = getFilterList(constraint, countryList)
                    filterResults.apply {
                        values = filterList
                        count = filterList.size
                    }
                }
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    if (it.count == 0) {
                        countryListener.onSearchNotFound()
                    } else {
                        countryListener.onSearchFound()
                        countryFilterList = it.values as ArrayList<Country>
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun getFilterList(
        constraint: CharSequence,
        countryList: ArrayList<Country>
    ): List<Country> {
        return countryList.filter { it.name.contains(constraint, true) }
    }

    interface CountryListListener {
        fun onClickItem(country: Country)
        fun onSearchNotFound()
        fun onSearchFound()
        fun hideLoading()
    }
}