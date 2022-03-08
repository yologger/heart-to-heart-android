package com.yologger.domain.usecase.email_verification_code

sealed class EmailVerificationCodeResult {
    object SUCCESS : EmailVerificationCodeResult()
    data class FAILURE(val error: EmailVerificationCodeError) : EmailVerificationCodeResult()
}