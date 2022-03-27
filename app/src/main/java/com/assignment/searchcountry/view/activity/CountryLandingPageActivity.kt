package com.assignment.searchcountry.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.searchcountry.databinding.CountrySearchLandingPageBinding
import com.assignment.searchcountry.model.Country
import com.assignment.searchcountry.presenter.CountrySearchInterface
import com.assignment.searchcountry.presenter.CountrySearchPresenter
import com.assignment.searchcountry.view.adapter.CountryListAdapter

class CountryLandingPageActivity : AppCompatActivity(), CountrySearchInterface {

    private lateinit var binding: CountrySearchLandingPageBinding

    private lateinit var countryListAdapter: CountryListAdapter

    private val presenter = CountrySearchPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CountrySearchLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.setupView(applicationContext)
    }

    override fun setupAdapter(countryList: ArrayList<Country>) {
        countryListAdapter = CountryListAdapter(countryList)
        binding.rvCountryList.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = countryListAdapter
        }
    }
}