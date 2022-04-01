package com.yologger.domain.usecase.auth.verifyAccessToken

enum class VerifyAccessTokenResponseError {
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_ACCESS_TOKEN,
    EXPIRED_ACCESS_TOKEN,
    ACCESS_TOKEN_EMPTY,
}