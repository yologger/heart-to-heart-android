package com.yologger.domain.usecase.post.register_post

enum class RegisterPostResultError {
    NETWORK_ERROR,
    CLIENT_ERROR,
    FILE_UPLOAD_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    FILE_SIZE_LIMIT_EXCEEDED
}