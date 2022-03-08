package com.yologger.data.datasource.api.test

import retrofit2.Call
import retrofit2.http.GET

interface TestApi {
    @GET("/test/test1")
    fun test1(): Call<TestResponse>

    @GET("/test/test2")
    fun test2(): Call<TestResponse>
}