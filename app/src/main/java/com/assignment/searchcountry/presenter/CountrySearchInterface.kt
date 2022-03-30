package com.assignment.searchcountry.presenter

import com.assignment.searchcountry.model.Country

interface CountrySearchInterface {
    fun setupAdapter(countryList: List<Country>)
    fun setupSearch()
    fun updateCountryList(countryList: List<Country>)
    fun onSearchNotFound()
    fun onSearchFound()
    fun showLoading()
}