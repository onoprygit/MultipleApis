package com.onopry.ritmultipleapis.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NationalizeItem(
    @Json(name = "country")
    val country: List<Country>,
    @Json(name = "name")
    val name: String
)