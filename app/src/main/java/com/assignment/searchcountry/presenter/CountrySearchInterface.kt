package com.assignment.searchcountry.presenter

import com.assignment.searchcountry.model.Country

interface CountrySearchInterface {
    fun setupAdapter(countryList: ArrayList<Country>)
}