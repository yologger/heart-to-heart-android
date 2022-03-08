package com.yologger.domain.repository

import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult

interface AuthRepository {
    fun emailVerificationCode(email: String): EmailVerificationCodeResult
}