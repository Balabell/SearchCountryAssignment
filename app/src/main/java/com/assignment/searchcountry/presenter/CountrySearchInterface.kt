package com.assignment.searchcountry.presenter

import com.assignment.searchcountry.model.Country

interface CountrySearchInterface {
    fun setupAdapter(countryList: ArrayList<Country>)
    fun setupSearch()
    fun showLoading()
    fun hideLoading()
}