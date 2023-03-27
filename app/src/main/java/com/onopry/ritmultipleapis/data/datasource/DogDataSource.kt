package com.onopry.ritmultipleapis.data.datasource

import com.onopry.ritmultipleapis.data.service.DogService
import com.onopry.ritmultipleapis.utils.NetworkConst
import com.onopry.ritmultipleapis.utils.wrapApiResponse
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DogDataSource(
    moshi: Moshi
) {
    private val dogHttpInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val dogHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(dogHttpInterceptor)
            .build()
    }

    private val dogRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConst.DOG_API)
            .client(dogHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private val dogApi: DogService by lazy {
        dogRetrofit.create(DogService::class.java)
    }

    suspend fun getSingleDog() = wrapApiResponse { dogApi.getDogPhoto() }
}