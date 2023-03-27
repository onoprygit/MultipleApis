package com.onopry.ritmultipleapis.data.datasource

import com.onopry.ritmultipleapis.data.service.DogService
import com.onopry.ritmultipleapis.data.service.OwnApi
import com.onopry.ritmultipleapis.utils.ApiResult
import com.onopry.ritmultipleapis.utils.NetworkConst
import com.onopry.ritmultipleapis.utils.wrapApiResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

class OwnDataSource() {
    private val ownHttpInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val ownHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ownHttpInterceptor)
            .build()
    }

    private val ownRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://asd.r")
            .client(ownHttpClient)
            .build()
    }

    private val ownApi: OwnApi by lazy { ownRetrofit.create(OwnApi::class.java) }

    suspend fun fetchOwnApi(url: String): ApiResult<String> = try {
        ApiResult.Success(
            data = ownApi.fetchOwnApi(url).string()
        )
    } catch (e: HttpException) {
        ApiResult.Error(code = e.code(), message = e.message)
    } catch (e: Throwable) {
        ApiResult.Exception(e)
    }
}
