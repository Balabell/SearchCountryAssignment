package com.assignment.searchcountry.presenter

import android.content.Context
import com.assignment.searchcountry.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class CountrySearchPresenter(private val view: CountrySearchInterface) {

    fun setupView(context: Context) {
//        view.showLoading()
        getCountryList(context)
    }

    private fun getCountryList(context: Context) {
        lateinit var jsonString: String
        try {
            jsonString =
                context.assets.open("cities/cities.json").bufferedReader().use { it.readText() }

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