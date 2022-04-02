package com.yologger.domain.usecase.auth.emailVerificationCode

sealed class EmailVerificationCodeResult {
    object SUCCESS : EmailVerificationCodeResult()
    data class FAILURE(val error: EmailVerificationCodeResultError) : EmailVerificationCodeResult()
}