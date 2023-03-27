package com.onopry.ritmultipleapis.data.service

import com.onopry.ritmultipleapis.utils.NetworkConst
import com.onopry.ritmultipleapis.data.model.DogResponse
import retrofit2.Response
import retrofit2.http.GET


interface DogService {
    @GET(NetworkConst.DOG_ENDPOINT)
    suspend fun getDogPhoto(): Response<DogResponse>
}