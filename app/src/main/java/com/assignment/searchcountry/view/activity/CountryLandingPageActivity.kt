package com.assignment.searchcountry.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.searchcountry.databinding.CountrySearchLandingPageBinding
import com.assignment.searchcountry.model.Coord
import com.assignment.searchcountry.model.Country
import com.assignment.searchcountry.view.adapter.CountryListAdapter
import java.io.IOException

class CountryLandingPageActivity : AppCompatActivity() {

    private lateinit var binding: CountrySearchLandingPageBinding

    private lateinit var countryListAdapter: CountryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CountrySearchLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        readJson()

        val countryList = arrayListOf(
            Country("UA", "Hurzuf", 707860, Coord(34.283333, 44.549999)),
            Country("AU", "Sydney", 707860, Coord(34.283333, 44.549999))
        )


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

    private fun readJson() {
        val jsonString: String
        try {
            jsonString = assets.open("cities.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
//            return null
        }
//        return jsonString
    }
}