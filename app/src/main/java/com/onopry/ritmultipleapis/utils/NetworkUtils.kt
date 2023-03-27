package com.onopry.ritmultipleapis.utils

import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> wrapApiResponse(
    request: suspend () -> Response<T>
//    request: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = request()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Error(
                code = response.code(),
                message = response.message()
            )
        }
    } catch (e: HttpException) {
        ApiResult.Error(code = e.code(), message = e.message)
    } catch (e: Throwable) {
        ApiResult.Exception(e)
    }
}

sealed class ApiResult<T : Any> {
    class Success<T : Any>(val data: T) : ApiResult<T>()
    class Error<T : Any>(val code: Int, val message: String?) : ApiResult<T>()
    class Exception<T : Any>(val exc: Throwable) : ApiResult<T>()
    class Empty<T : Any> : ApiResult<T>()
}