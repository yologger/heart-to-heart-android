package com.yologger.domain.repository

import com.yologger.domain.usecase.auth.confirm_verification_code.ConfirmVerificationCodeResult
import com.yologger.domain.usecase.auth.email_verification_code.EmailVerificationCodeResult
import com.yologger.domain.usecase.auth.join.JoinResult
import com.yologger.domain.usecase.auth.login.LoginResult
import com.yologger.domain.usecase.auth.logout.LogoutResult
import com.yologger.domain.usecase.auth.verify_access_token.ReissueTokenResponse
import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenResponse

interface AuthRepository {
    fun emailVerificationCode(email: String): EmailVerificationCodeResult
    fun confirmVerificationCode(email: String, code: String): ConfirmVerificationCodeResult
    fun join(email: String, name: String, nickname: String, password: String): JoinResult
    fun login(email: String, password: String): LoginResult
    fun reissueToken(): ReissueTokenResponse
    fun verifyAccessToken(): VerifyAccessTokenResponse
    fun clearSession()
    fun logout(): LogoutResult
}