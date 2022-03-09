package com.yologger.data.repository.auth

import com.google.gson.Gson
import com.yologger.data.datasource.api.auth.*
import com.yologger.data.datasource.pref.Session
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeError
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeResult
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeError
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult
import com.yologger.domain.usecase.join.JoinError
import com.yologger.domain.usecase.join.JoinResult
import com.yologger.domain.usecase.login.LoginError
import com.yologger.domain.usecase.login.LoginResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val gson: Gson,
    private val sessionStore: SessionStore
) : AuthRepository {

    private var session: Session? = null
        get() {
            return sessionStore.getSession()
        }

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

    override fun login(email: String, password: String): LoginResult {
        try {
            val request = LoginRequest(email = email, password = password)
            val response = authService.login(request).execute()
            return if(response.isSuccessful) {
                val successResponse = response.body()!!
                val session = Session(userId = successResponse.userId, email = email, name = successResponse.name, nickname = successResponse.nickname, accessToken = successResponse.accessToken, refreshToken = successResponse.refreshToken)
                saveSession(session)
                LoginResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), LoginFailureResponse::class.java)
                when (failureResponse.code) {
                    LoginFailureCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED -> LoginResult.FAILURE(LoginError.NETWORK_ERROR)
                    LoginFailureCode.INVALID_INPUT_VALUE -> LoginResult.FAILURE(LoginError.INVALID_INPUT_VALUE)
                    LoginFailureCode.NOT_FOUND -> LoginResult.FAILURE(LoginError.NETWORK_ERROR)
                    LoginFailureCode.INVALID_PASSWORD -> LoginResult.FAILURE(LoginError.INVALID_PASSWORD)
                    LoginFailureCode.MEMBER_NOT_EXIST -> LoginResult.FAILURE(LoginError.MEMBER_NOT_EXIST)
                }
            }
        } catch (e: Exception) {
            return LoginResult.FAILURE(LoginError.NETWORK_ERROR)
        }
    }

    private fun saveSession(session: Session) {
        sessionStore.putSession(session)
        this.session = session
    }

    private fun clearSession() {
        sessionStore.deleteSession()
        this.session = null
    }
}