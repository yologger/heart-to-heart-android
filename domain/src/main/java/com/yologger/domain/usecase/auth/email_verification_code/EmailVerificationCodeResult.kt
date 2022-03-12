package com.yologger.domain.usecase.auth.email_verification_code

sealed class EmailVerificationCodeResult {
    object SUCCESS : EmailVerificationCodeResult()
    data class FAILURE(val error: EmailVerificationCodeResultError) : EmailVerificationCodeResult()
}