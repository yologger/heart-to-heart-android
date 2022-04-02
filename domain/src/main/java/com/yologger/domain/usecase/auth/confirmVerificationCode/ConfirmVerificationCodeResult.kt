package com.yologger.domain.usecase.auth.confirmVerificationCode

sealed class ConfirmVerificationCodeResult {
    object SUCCESS: ConfirmVerificationCodeResult()
    data class FAILURE(val error: ConfirmVerificationCodeResultError): ConfirmVerificationCodeResult()
}