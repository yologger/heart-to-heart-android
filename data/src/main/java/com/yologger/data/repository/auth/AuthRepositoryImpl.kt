package com.yologger.data.repository.auth

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.yologger.data.datasource.api.auth.*
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeError
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeResult
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeError
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult
import com.yologger.domain.usecase.join.JoinError
import com.yologger.domain.usecase.join.JoinResult
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
        try {
            val response = authService.emailVerificationCode(request).execute()
            return if (response.isSuccessful) {
                EmailVerificationCodeResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), EmailVerificationCodeFailureResponse::class.java)
                return when (failureResponse.code) {
                    EmailVerificationCodeFailureCode.MEMBER_ALREADY_EXISTS -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.MEMBER_ALREADY_EXISTS)
                    EmailVerificationCodeFailureCode.INVALID_INPUT_VALUE -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.INVALID_INPUT_VALUE)
                    else -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.UNKNOWN_SERVER_ERROR)
                }
            }
        } catch (e: Exception) {
            return EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.NETWORK_ERROR)
        }
    }

    override fun confirmVerificationCode(email: String, code: String): ConfirmVerificationCodeResult {
        val request = ConfirmVerificationCodeRequest(email, code)
        try {
            val response = authService.confirmVerificationCode(request).execute()
            return if (response.isSuccessful) {
                ConfirmVerificationCodeResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), ConfirmVerificationCodeFailureResponse::class.java)
                when (failureResponse.code) {
                    ConfirmVerificationCodeFailureCode.INVALID_EMAIL -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeError.INVALID_EMAIL)
                    ConfirmVerificationCodeFailureCode.INVALID_VERIFICATION_CODE -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeError.INVALID_VERIFICATION_CODE)
                    ConfirmVerificationCodeFailureCode.EXPIRED_VERIFICATION_CODE -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeError.EXPIRED_VERIFICATION_CODE)
                    else -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeError.UNKNOWN_SERVER_ERROR)
                }
            }
        } catch (e: Exception) {
            return ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeError.NETWORK_ERROR)
        }
    }

    override fun join(email: String, name: String, nickname: String, password: String): JoinResult {
        try {
            val request = JoinRequest(email = email, name = name, nickname = nickname, password = password)
            val response = authService.join(request).execute()
            return if (response.isSuccessful) {
                JoinResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), JoinFailureResponse::class.java)
                when (failureResponse.code) {
                    JoinFailureCode.MEMBER_ALREADY_EXISTS -> JoinResult.FAILURE(JoinError.MEMBER_ALREADY_EXISTS)
                    JoinFailureCode.INVALID_INPUT_VALUE -> JoinResult.FAILURE(JoinError.INVALID_INPUT_VALUE)
                    JoinFailureCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED -> JoinResult.FAILURE(JoinError.NETWORK_ERROR)
                    JoinFailureCode.NOT_FOUND -> JoinResult.FAILURE(JoinError.NETWORK_ERROR)
                    else -> JoinResult.FAILURE(JoinError.UNKNOWN_SERVER_ERROR)
                }
            }
        } catch (e: Exception) {
            return JoinResult.FAILURE(JoinError.NETWORK_ERROR)
        }
    }


}