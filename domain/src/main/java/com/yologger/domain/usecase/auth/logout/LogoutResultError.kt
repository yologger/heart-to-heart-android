package com.yologger.domain.usecase.auth.logout

enum class LogoutResultError {
    CLIENT_ERROR,
    NETWORK_ERROR,
    INVALID_ACCESS_TOKEN,
    JSON_PARSE_ERROR
}