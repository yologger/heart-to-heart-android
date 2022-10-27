package com.yologger.data.datasource.api.test

import retrofit2.Call
import retrofit2.http.GET

interface TestApi {
    @GET("test/get")
    fun getTest(): Call<GetTestResponse>
}