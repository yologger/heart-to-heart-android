package com.yologger.domain.usecase.auth.login

enum class LoginResultError {
    NETWORK_ERROR,
    CLIENT_ERROR,
    MEMBER_NOT_EXIST,
    INVALID_PASSWORD,
    INVALID_PARAMS
}