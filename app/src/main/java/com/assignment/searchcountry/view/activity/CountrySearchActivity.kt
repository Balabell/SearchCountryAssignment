package com.assignment.searchcountry.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        showLoading()
        presenter.setupView(applicationContext)
    }



//    }

    override fun setupSearch() {
        binding.svCountrySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countryListAdapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun showLoading() {

        var inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration = 200
        binding.progressBarHolder.animation = inAnimation
        binding.progressBarHolder.visibility = VISIBLE


//        binding.pbLoading.visibility = VISIBLE
    }

    override fun hideLoading() {
        var inAnimation = AlphaAnimation(1f, 0f)
        inAnimation.duration = 200
        binding.progressBarHolder.animation = inAnimation
        binding.progressBarHolder.visibility = GONE
//        binding.pbLoading.visibility = GONE
    }

    override fun setupAdapter(countryList: ArrayList<Country>) {
        countryListAdapter = CountryListAdapter(countryList, this)
        binding.rvCountryList.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = countryListAdapter
//            hideLoading()
        }
    }

    override fun onClickItem(country: Country) {
        val gmmIntentUri = Uri.parse(
            "geo:" + country.coord.lat + "," + country.coord.lon + "?q=" +
                    country.name + country.country
        )
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onSearchNotFound() {
        binding.apply {
            rvCountryList.visibility = GONE
            llNoSearchFound.visibility = VISIBLE
        }
    }

    override fun onSearchFound() {
        binding.apply {
            rvCountryList.visibility = VISIBLE
            llNoSearchFound.visibility = GONE
        }
    }
}