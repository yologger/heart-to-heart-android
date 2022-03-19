package com.yologger.domain.usecase.auth.join

enum class JoinResultError {
    NETWORK_ERROR,
    CLIENT_ERROR,
    MEMBER_ALREADY_EXIST,
    INVALID_PARAMS,
    JSON_PARSE_ERROR
}