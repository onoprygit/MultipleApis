package com.onopry.ritmultipleapis.data.service

import com.onopry.ritmultipleapis.data.model.NationalizeItem
import com.onopry.ritmultipleapis.utils.NetworkConst
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NationalizeService {

//    @GET(NetworkConst.NATIONALS_API)
//    suspend fun getNationalize(
//        @Query("name") vararg name: String
//    ): Response<List<NationalizeItem>>

    @GET
    suspend fun getNationalize(@Url url: String): Response<List<NationalizeItem>>
}