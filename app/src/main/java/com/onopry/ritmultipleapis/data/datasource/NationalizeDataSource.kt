package com.onopry.ritmultipleapis.data.datasource

import com.onopry.ritmultipleapis.data.model.NationalizeItem
import com.onopry.ritmultipleapis.data.service.NationalizeService
import com.onopry.ritmultipleapis.utils.ApiResult
import com.onopry.ritmultipleapis.utils.NetworkConst
import com.onopry.ritmultipleapis.utils.wrapApiResponse
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NationalizeDataSource(moshi: Moshi) {
    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConst.NATIONALS_API)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private val apiService: NationalizeService =
        retrofit.create(NationalizeService::class.java)

    suspend fun getNationalize(vararg name: String): ApiResult<List<NationalizeItem>> {
        val url = StringBuilder("https://api.nationalize.io/?")
        if (name.size == 1) {
            url.append("name[]=${name.first()}")
        } else {
            name.forEachIndexed { i, nameItem ->
                if (i == 0) {
                    url.append("name[]=$nameItem")
                } else {
                    url.append("&name[]=$nameItem")
                }
            }
        }
        return wrapApiResponse {
            apiService.getNationalize(url.toString())
        }
    }

//    https://api.nationalize.io/?name[]=michael&name[]=matthew&name[]=jane
//    https://api.nationalize.io/?name=michael
}