package com.yologger.domain.usecase.logout

enum class LogoutError {
    NETWORK_ERROR,
    INVALID_INPUT_VALUE,
    INVALID_ACCESS_TOKEN,
    ACCESS_TOKEN_EMPTY
}