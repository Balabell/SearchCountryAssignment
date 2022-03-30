package com.assignment.searchcountry.presenter

import com.assignment.searchcountry.model.Country

interface CountrySearchInterface {
    fun setupAdapter(countryList: List<Country>)
    fun setupSearch()
    fun onSearchNotFound()
    fun onSearchFound(countryList: List<Country>)
    fun showLoading()
}