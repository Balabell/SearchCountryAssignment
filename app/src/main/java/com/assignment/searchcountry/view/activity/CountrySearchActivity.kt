package com.assignment.searchcountry.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.searchcountry.databinding.ActivityCountrySearchBinding
import com.assignment.searchcountry.model.Country
import com.assignment.searchcountry.presenter.CountrySearchInterface
import com.assignment.searchcountry.presenter.CountrySearchPresenter
import com.assignment.searchcountry.view.adapter.CountryListAdapter

class CountrySearchActivity : AppCompatActivity(),
    CountrySearchInterface,
    CountryListAdapter.CountryListListener {

    private lateinit var binding: ActivityCountrySearchBinding
    private lateinit var countryListAdapter: CountryListAdapter
    private val presenter = CountrySearchPresenter(this)

    companion object {
        private const val MAP_PACKAGE_NAME = "com.google.android.apps.maps"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.setupView(applicationContext)
    }

    override fun setupSearch() {
        binding.svCountrySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.onQueryTextChange(newText, countryListAdapter.getCountryList())
                return false
            }
        })
    }

    override fun updateCountryList(countryList: List<Country>) {
        countryListAdapter.updateCountryList(countryList.toMutableList())
    }

    override fun showLoading() {
        binding.apply {
            pbLoading.visibility = VISIBLE
            rvCountryList.visibility = GONE
        }
    }

    override fun setupAdapter(countryList: List<Country>) {
        countryListAdapter = CountryListAdapter(countryList.toMutableList(), this)
        binding.rvCountryList.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = countryListAdapter
        }
    }

    override fun onClickItem(country: Country) {
        val uri = Uri.parse(
            "geo:${country.coord.lat},${country.coord.lon}?q=${country.name}${country.country}"
        )
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage(MAP_PACKAGE_NAME)
        startActivity(mapIntent)
    }

    override fun onSearchNotFound() {
        hideLoading()
        binding.apply {
            rvCountryList.visibility = GONE
            llNoSearchFound.visibility = VISIBLE
        }
    }

    override fun onSearchFound() {
        hideLoading()
        binding.apply {
            rvCountryList.visibility = VISIBLE
            llNoSearchFound.visibility = GONE
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbLoading.visibility = GONE
            rvCountryList.visibility = VISIBLE
        }
    }
}