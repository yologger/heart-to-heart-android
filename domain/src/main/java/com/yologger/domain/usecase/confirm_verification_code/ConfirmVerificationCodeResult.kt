package com.yologger.domain.usecase.confirm_verification_code

sealed class ConfirmVerificationCodeResult {
    object SUCCESS: ConfirmVerificationCodeResult()
    data class FAILURE(val error: ConfirmVerificationCodeError): ConfirmVerificationCodeResult()
}