package com.onopry.ritmultipleapis.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "country_id")
    val countryId: String,
    @Json(name = "probability")
    val probability: Double
)