package com.assignment.searchcountry.model

import com.google.gson.annotations.SerializedName

data class Country(
    val country: String,
    val name: String,
    @SerializedName("_id")
    val id: Int,
    val coord: Coord
)