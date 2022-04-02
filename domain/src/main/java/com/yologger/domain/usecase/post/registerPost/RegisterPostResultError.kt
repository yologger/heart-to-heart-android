package com.yologger.domain.usecase.post.registerPost

enum class RegisterPostResultError {
    NETWORK_ERROR,
    CLIENT_ERROR,
    FILE_UPLOAD_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    FILE_SIZE_LIMIT_EXCEEDED,
    JSON_PARSE_ERROR
}