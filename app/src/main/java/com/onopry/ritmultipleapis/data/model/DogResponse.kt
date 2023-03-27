package com.onopry.ritmultipleapis.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogResponse(
    @Json(name = "message")
    val imageUrl: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "code")
    val code: Int?
)
