package com.yologger.data.datasource.api.auth

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/emailVerificationCode")
    fun emailVerificationCode(@Body request: EmailVerificationCodeRequest): Call<EmailVerificationCodeResponse>

    @POST("/auth/confirmVerificationCode")
    fun confirmVerificationCode(@Body request: ConfirmVerificationCodeRequest): Call<ConfirmVerificationCodeResponse>
}