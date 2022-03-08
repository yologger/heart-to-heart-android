package com.yologger.data.repository.auth

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.yologger.data.datasource.api.auth.*
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeError
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val gson: Gson
) : AuthRepository {

    override fun emailVerificationCode(email: String): EmailVerificationCodeResult {
        val request = EmailVerificationCodeRequest(email)
        val response = authService.emailVerificationCode(request).execute()
        try {
            return if (response.isSuccessful) {
                return EmailVerificationCodeResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), EmailVerificationCodeFailureResponse::class.java)
                when (failureResponse.code) {
                    EmailVerificationCodeFailureCode.MEMBER_ALREADY_EXISTS -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.MEMBER_ALREADY_EXISTS)
                    EmailVerificationCodeFailureCode.INVALID_INPUT_VALUE -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.INVALID_INPUT_VALUE)
                    else -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.UNKNOWN_SERVER_ERROR)
                }
            }
        } catch (e: Exception) {
            return EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.NETWORK_ERROR)
        }
    }
}