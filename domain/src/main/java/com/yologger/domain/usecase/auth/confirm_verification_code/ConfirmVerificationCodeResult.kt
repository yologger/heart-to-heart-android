package com.yologger.domain.usecase.auth.confirm_verification_code

sealed class ConfirmVerificationCodeResult {
    object SUCCESS: ConfirmVerificationCodeResult()
    data class FAILURE(val error: ConfirmVerificationCodeResultError): ConfirmVerificationCodeResult()
}