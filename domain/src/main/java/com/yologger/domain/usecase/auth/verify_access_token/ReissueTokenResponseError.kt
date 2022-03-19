package com.yologger.domain.usecase.auth.verify_access_token

enum class ReissueTokenResponseError {
    CLIENT_ERROR,
    NETWORK_ERROR,
    EMPTY_REFRESH_TOKEN,
    INVALID_REFRESH_TOKEN,
    EXPIRED_REFRESH_TOKEN,
    MEMBER_NOT_EXIST,
    JSON_PARSE_ERROR
}