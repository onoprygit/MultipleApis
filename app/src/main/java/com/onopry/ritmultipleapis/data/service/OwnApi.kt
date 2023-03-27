package com.onopry.ritmultipleapis.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface OwnApi {
    @GET
    suspend fun fetchOwnApi(@Url url: String): ResponseBody
}