package com.yologger.domain.usecase.login

enum class LoginError {
    NETWORK_ERROR,
    UNKNOWN_SERVER_ERROR,
    INVALID_INPUT_VALUE,
    MEMBER_NOT_EXIST,
    INVALID_PASSWORD
}