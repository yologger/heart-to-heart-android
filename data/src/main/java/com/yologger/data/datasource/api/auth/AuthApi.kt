package com.yologger.data.datasource.api.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/v1/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}