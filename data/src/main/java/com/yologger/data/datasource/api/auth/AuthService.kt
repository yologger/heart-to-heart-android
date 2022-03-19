package com.yologger.data.datasource.api.auth

import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeSuccessResponse
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeSuccessResponse
import com.yologger.data.datasource.api.auth.model.join.JoinRequest
import com.yologger.data.datasource.api.auth.model.join.JoinSuccessResponse
import com.yologger.data.datasource.api.auth.model.login.LoginRequest
import com.yologger.data.datasource.api.auth.model.login.LoginSuccessResponse
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