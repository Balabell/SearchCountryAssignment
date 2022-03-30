package com.assignment.searchcountry.presenter

import android.content.Context
import com.assignment.searchcountry.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class CountrySearchPresenter(private val view: CountrySearchInterface) {

    companion object {
        private const val FILE_NAME = "cities/cities.json"
    }

    fun setupView(context: Context) {
        getCountryList(context)
    }

    fun onQueryTextChange(input: String?, countryList: List<Country>) {
        view.showLoading()
        if (input.isNullOrEmpty()) {
            view.apply {
                updateCountryList(countryList)
                onSearchFound()
            }
        } else {
            val filterList = getFilterList(input, countryList)
            if (filterList.size == 0) {
                view.onSearchNotFound()
            } else {
                view.apply {
                    updateCountryList(filterList.sortedBy { it.name })
                    onSearchFound()
                }
            }
        }
    }

    private fun getFilterList(
        input: String,
        countryList: List<Country>
    ): MutableList<Country> {
        return countryList.filter { it.name.contains(input, true) }.toMutableList()
    }

    private fun getCountryList(context: Context) {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open(FILE_NAME)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return
        }
        val listCountryType = object : TypeToken<ArrayList<Country>>() {}.type
        val countryList: ArrayList<Country> = Gson().fromJson(jsonString, listCountryType)

        countryList.sortBy {
            it.name
        }

        view.apply {
            setupAdapter(countryList)
            setupSearch()
        }
    }
}