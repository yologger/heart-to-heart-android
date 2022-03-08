package com.yologger.domain.usecase.confirm_verification_code

enum class ConfirmVerificationCodeError {
    NETWORK_ERROR,
    UNKNOWN_SERVER_ERROR,
    INVALID_EMAIL,
    EXPIRED_VERIFICATION_CODE,
    INVALID_VERIFICATION_CODE,
    INVALID_INPUT
}