package com.yologger.domain.repository

import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeResult
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult
import com.yologger.domain.usecase.join.JoinResult
import com.yologger.domain.usecase.login.LoginResult

interface AuthRepository {
    fun emailVerificationCode(email: String): EmailVerificationCodeResult
    fun confirmVerificationCode(email: String, code: String): ConfirmVerificationCodeResult
    fun join(email: String, name: String, nickname: String, password: String): JoinResult
    fun login(email: String, password: String): LoginResult
}