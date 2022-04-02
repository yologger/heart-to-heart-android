package com.yologger.domain.usecase.auth.confirmVerificationCode

enum class ConfirmVerificationCodeResultError {
    NETWORK_ERROR,
    CLIENT_ERROR,

    INVALID_EMAIL,
    EXPIRED_VERIFICATION_CODE,
    INVALID_VERIFICATION_CODE,
    INVALID_PARAMETERS,
    JSON_PARSE_ERROR
}