package com.yologger.data.repository.auth

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.yologger.data.datasource.api.auth.AuthService
import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeFailureResponse
import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeFailureResponseCode
import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeFailureResponse
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeFailureResponseCode
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.join.JoinFailureResponse
import com.yologger.data.datasource.api.auth.model.join.JoinFailureResponseCode
import com.yologger.data.datasource.api.auth.model.join.JoinRequest
import com.yologger.data.datasource.api.auth.model.login.LoginFailureResponse
import com.yologger.data.datasource.api.auth.model.login.LoginFailureResponseCode
import com.yologger.data.datasource.api.auth.model.login.LoginRequest
import com.yologger.data.datasource.api.auth.model.logout.LogoutFailureResponse
import com.yologger.data.datasource.api.auth.model.logout.LogoutFailureResponseCode
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenFailureResponse
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenFailureResponseCode
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenRequest
import com.yologger.data.datasource.api.auth.model.verify_access_token.VerifyAccessTokenFailureResponse
import com.yologger.data.datasource.api.auth.model.verify_access_token.VerifyAccessTokenFailureResponseCode
import com.yologger.data.datasource.pref.Session
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.usecase.auth.confirm_verification_code.ConfirmVerificationCodeResult
import com.yologger.domain.usecase.auth.confirm_verification_code.ConfirmVerificationCodeResultError
import com.yologger.domain.usecase.auth.email_verification_code.EmailVerificationCodeResult
import com.yologger.domain.usecase.auth.email_verification_code.EmailVerificationCodeResultError
import com.yologger.domain.usecase.auth.join.JoinResult
import com.yologger.domain.usecase.auth.join.JoinResultError
import com.yologger.domain.usecase.auth.login.LoginResult
import com.yologger.domain.usecase.auth.login.LoginResultError
import com.yologger.domain.usecase.auth.logout.LogoutResult
import com.yologger.domain.usecase.auth.logout.LogoutResultError
import com.yologger.domain.usecase.auth.verify_access_token.ReissueTokenResponse
import com.yologger.domain.usecase.auth.verify_access_token.ReissueTokenResponseError
import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenResponse
import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenResponseError
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val gson: Gson,
    private val sessionStore: SessionStore
) : AuthRepository {

    override fun emailVerificationCode(email: String): EmailVerificationCodeResult {
        return try {
            val request = EmailVerificationCodeRequest(email)
            val response = authService.emailVerificationCode(request).execute()
            if (response.isSuccessful) {
                EmailVerificationCodeResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), EmailVerificationCodeFailureResponse::class.java)
                return when (failureResponse.code) {
                    EmailVerificationCodeFailureResponseCode.MEMBER_ALREADY_EXIST -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeResultError.MEMBER_ALREADY_EXIST)
                    EmailVerificationCodeFailureResponseCode.MAIL_SYSTEM_ERROR -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeResultError.MAIL_ERROR)
                    else -> EmailVerificationCodeResult.FAILURE(EmailVerificationCodeResultError.CLIENT_ERROR)
                }
            }
        } catch (e: JsonParseException) {
            return EmailVerificationCodeResult.FAILURE(EmailVerificationCodeResultError.JSON_PARSE_ERROR)
        } catch (e: Exception) {
            return EmailVerificationCodeResult.FAILURE(EmailVerificationCodeResultError.NETWORK_ERROR)
        }
    }

    override fun confirmVerificationCode(email: String, code: String): ConfirmVerificationCodeResult {
        return try {
            val request = ConfirmVerificationCodeRequest(email, code)
            val response = authService.confirmVerificationCode(request).execute()
            if (response.isSuccessful) {
                ConfirmVerificationCodeResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(
                    response.errorBody()!!.string(),
                    ConfirmVerificationCodeFailureResponse::class.java
                )
                when (failureResponse.code) {
                    ConfirmVerificationCodeFailureResponseCode.INVALID_EMAIL -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.INVALID_EMAIL)
                    ConfirmVerificationCodeFailureResponseCode.INVALID_VERIFICATION_CODE -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.INVALID_VERIFICATION_CODE)
                    ConfirmVerificationCodeFailureResponseCode.EXPIRED_VERIFICATION_CODE -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.EXPIRED_VERIFICATION_CODE)
                    else -> ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.CLIENT_ERROR)
                }
            }
        } catch (e: JsonParseException) {
            ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.JSON_PARSE_ERROR)
        } catch (e: Exception) {
            ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.NETWORK_ERROR)
        }
    }

    override fun join(email: String, name: String, nickname: String, password: String): JoinResult {
        return try {
            val request =
                JoinRequest(email = email, name = name, nickname = nickname, password = password)
            val response = authService.join(request).execute()
            if (response.isSuccessful) {
                JoinResult.SUCCESS
            } else {
                val failureResponse =
                    gson.fromJson(response.errorBody()!!.string(), JoinFailureResponse::class.java)
                when (failureResponse.code) {
                    JoinFailureResponseCode.MEMBER_ALREADY_EXIST -> JoinResult.FAILURE(
                        JoinResultError.MEMBER_ALREADY_EXIST
                    )
                    else -> JoinResult.FAILURE(JoinResultError.CLIENT_ERROR)
                }
            }
        } catch (e: JsonParseException) {
            JoinResult.FAILURE(JoinResultError.JSON_PARSE_ERROR)
        } catch (e: Exception) {
            JoinResult.FAILURE(JoinResultError.NETWORK_ERROR)
        }
    }

    override fun login(email: String, password: String): LoginResult {
        return try {
            val request = LoginRequest(email = email, password = password)
            val response = authService.login(request).execute()
            if (response.isSuccessful) {
                val successResponse = response.body()!!
                val session = Session(memberId = successResponse.memberId, email = email, name = successResponse.name, nickname = successResponse.nickname, accessToken = successResponse.accessToken, refreshToken = successResponse.refreshToken)
                sessionStore.putSession(session)
                LoginResult.SUCCESS
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), LoginFailureResponse::class.java)
                when (failureResponse.code) {
                    LoginFailureResponseCode.INVALID_PASSWORD -> LoginResult.FAILURE(LoginResultError.INVALID_PASSWORD)
                    LoginFailureResponseCode.MEMBER_NOT_EXIST -> LoginResult.FAILURE(LoginResultError.MEMBER_NOT_EXIST)
                    else -> LoginResult.FAILURE(LoginResultError.CLIENT_ERROR)
                }
            }
        } catch (e: Exception) {
            return LoginResult.FAILURE(LoginResultError.NETWORK_ERROR)
        }
    }

    override fun verifyAccessToken(): VerifyAccessTokenResponse {
        sessionStore.getSession()?.let {
            return try {
                val response = authService.verifyAccessToken("Bearer ${it.accessToken}").execute()
                if (response.isSuccessful) {
                    VerifyAccessTokenResponse.SUCCESS
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), VerifyAccessTokenFailureResponse::class.java)
                    when(failureResponse.code) {
                        VerifyAccessTokenFailureResponseCode.EXPIRED_ACCESS_TOKEN -> VerifyAccessTokenResponse.FAILURE(VerifyAccessTokenResponseError.EXPIRED_ACCESS_TOKEN)
                        VerifyAccessTokenFailureResponseCode.INVALID_ACCESS_TOKEN -> VerifyAccessTokenResponse.FAILURE(VerifyAccessTokenResponseError.INVALID_ACCESS_TOKEN)
                        VerifyAccessTokenFailureResponseCode.ACCESS_TOKEN_EMPTY -> VerifyAccessTokenResponse.FAILURE(VerifyAccessTokenResponseError.ACCESS_TOKEN_EMPTY)
                        else -> VerifyAccessTokenResponse.FAILURE(VerifyAccessTokenResponseError.CLIENT_ERROR)
                    }
                }
            } catch (e: Exception) {
                VerifyAccessTokenResponse.FAILURE(VerifyAccessTokenResponseError.NETWORK_ERROR)
            }
        }.run {
            return VerifyAccessTokenResponse.FAILURE(VerifyAccessTokenResponseError.ACCESS_TOKEN_EMPTY)
        }
    }

    override fun clearSession() = sessionStore.deleteSession()

    override fun reissueToken(): ReissueTokenResponse {
        sessionStore.getSession()?.let {
            try {
                val request = ReissueTokenRequest(memberId = it.memberId, refreshToken = it!!.refreshToken)
                val response = authService.reissueToken(request).execute()
                return if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    val newSession = Session(
                        memberId = successResponse.memberId,
                        name = successResponse.name,
                        nickname = successResponse.nickname,
                        accessToken = successResponse.accessToken,
                        refreshToken = successResponse.refreshToken,
                        email = successResponse.email
                    )
                    sessionStore.putSession(newSession)
                    ReissueTokenResponse.SUCCESS
                } else {
                    val failureResponse = gson.fromJson(
                        response.errorBody()!!.string(),
                        ReissueTokenFailureResponse::class.java
                    )
                    when (failureResponse.code) {
                        ReissueTokenFailureResponseCode.INVALID_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            ReissueTokenResponse.FAILURE(ReissueTokenResponseError.INVALID_REFRESH_TOKEN)
                        }
                        ReissueTokenFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            ReissueTokenResponse.FAILURE(ReissueTokenResponseError.EXPIRED_REFRESH_TOKEN)
                        }
                        ReissueTokenFailureResponseCode.MEMBER_NOT_EXIST -> {
                            sessionStore.deleteSession()
                            ReissueTokenResponse.FAILURE(ReissueTokenResponseError.MEMBER_NOT_EXIST)
                        }
                        else -> ReissueTokenResponse.FAILURE(ReissueTokenResponseError.CLIENT_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                return ReissueTokenResponse.FAILURE(ReissueTokenResponseError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                return ReissueTokenResponse.FAILURE(ReissueTokenResponseError.NETWORK_ERROR)
            }
        } ?: run {
            return ReissueTokenResponse.FAILURE(ReissueTokenResponseError.EMPTY_REFRESH_TOKEN)
        }
    }

    override fun logout(): LogoutResult {
        return try {
            sessionStore.getSession()?.let {
                val response = authService.logout("Bearer ${it.accessToken}").execute()
                if (response.isSuccessful) {
                    sessionStore.deleteSession()
                    LogoutResult.SUCCESS
                } else {
                    val failureResponse = gson.fromJson(
                        response.errorBody()!!.string(),
                        LogoutFailureResponse::class.java
                    )
                    when (failureResponse.code) {
                        LogoutFailureResponseCode.INVALID_ACCESS_TOKEN -> {
                            sessionStore.deleteSession()
                            LogoutResult.FAILURE(LogoutResultError.INVALID_ACCESS_TOKEN)
                        }
                        LogoutFailureResponseCode.EXPIRED_ACCESS_TOKEN -> {
                            sessionStore.deleteSession()
                            LogoutResult.FAILURE(LogoutResultError.INVALID_ACCESS_TOKEN)
                        }
                        LogoutFailureResponseCode.BEARER_NOT_INCLUDED -> {
                            sessionStore.deleteSession()
                            LogoutResult.FAILURE(LogoutResultError.INVALID_ACCESS_TOKEN)
                        }
                        else -> LogoutResult.FAILURE(LogoutResultError.CLIENT_ERROR)
                    }
                }
            } ?: run {
                return LogoutResult.SUCCESS
            }
        } catch (e: JsonParseException) {
            return LogoutResult.FAILURE(LogoutResultError.JSON_PARSE_ERROR)
        } catch (e: Exception) {
            return LogoutResult.FAILURE(LogoutResultError.NETWORK_ERROR)
        }
    }


}