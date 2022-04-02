package com.yologger.data.datasource.api.auth

import com.yologger.data.datasource.api.auth.model.confirmVerificationCode.ConfirmVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.confirmVerificationCode.ConfirmVerificationCodeSuccessResponse
import com.yologger.data.datasource.api.auth.model.emailVerificationCode.EmailVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.emailVerificationCode.EmailVerificationCodeSuccessResponse
import com.yologger.data.datasource.api.auth.model.join.JoinRequest
import com.yologger.data.datasource.api.auth.model.join.JoinSuccessResponse
import com.yologger.data.datasource.api.auth.model.login.LoginRequest
import com.yologger.data.datasource.api.auth.model.login.LoginSuccessResponse
import com.yologger.data.datasource.api.auth.model.logout.LogoutResponse
import com.yologger.data.datasource.api.auth.model.reissueToken.ReissueTokenRequest
import com.yologger.data.datasource.api.auth.model.reissueToken.ReissueTokenSuccessResponse
import com.yologger.data.datasource.api.auth.model.verifyAccessToken.VerifyAccessTokenSuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/emailVerificationCode")
    fun emailVerificationCode(@Body request: EmailVerificationCodeRequest): Call<EmailVerificationCodeSuccessResponse>

    @POST("/auth/confirmVerificationCode")
    fun confirmVerificationCode(@Body request: ConfirmVerificationCodeRequest): Call<ConfirmVerificationCodeSuccessResponse>

    @POST("/auth/join")
    fun join(@Body request: JoinRequest): Call<JoinSuccessResponse>

    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginSuccessResponse>

    @GET("/auth/verifyAccessToken")
    fun verifyAccessToken(@Header("Authorization") accessToken: String): Call<VerifyAccessTokenSuccessResponse>

    @POST("/auth/reissueToken")
    fun reissueToken(@Body request: ReissueTokenRequest): Call<ReissueTokenSuccessResponse>

    @POST("/auth/logout")
    fun logout(@Header("Authorization") accessToken: String): Call<LogoutResponse>
}