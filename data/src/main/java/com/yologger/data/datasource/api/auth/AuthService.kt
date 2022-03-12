package com.yologger.data.datasource.api.auth

import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeResponse
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeResponse
import com.yologger.data.datasource.api.auth.model.join.JoinRequest
import com.yologger.data.datasource.api.auth.model.join.JoinResponse
import com.yologger.data.datasource.api.auth.model.login.LoginRequest
import com.yologger.data.datasource.api.auth.model.login.LoginResponse
import com.yologger.data.datasource.api.auth.model.logout.LogoutResponse
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenRequest
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenSuccessResponse
import com.yologger.data.datasource.api.auth.model.verify_access_token.VerifyAccessTokenSuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/emailVerificationCode")
    fun emailVerificationCode(@Body request: EmailVerificationCodeRequest): Call<EmailVerificationCodeResponse>

    @POST("/auth/confirmVerificationCode")
    fun confirmVerificationCode(@Body request: ConfirmVerificationCodeRequest): Call<ConfirmVerificationCodeResponse>

    @POST("/auth/join")
    fun join(@Body request: JoinRequest): Call<JoinResponse>

    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/auth/logout")
    fun logout(@Header("Authorization") accessToken: String): Call<LogoutResponse>

    @GET("/auth/verifyAccessToken")
    fun verifyAccessToken(@Header("Authorization") accessToken: String): Call<VerifyAccessTokenSuccessResponse>

    @POST("/auth/reissueToken")
    fun reissueToken(@Body request: ReissueTokenRequest): Call<ReissueTokenSuccessResponse>
}