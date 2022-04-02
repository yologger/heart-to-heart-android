package com.yologger.domain.usecase.post.deletePost

enum class DeletePostResultError {
    // Common Error
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    JSON_PARSE_ERROR,

    // Business Error
    NO_POST_EXIST,
    FILE_UPLOAD_ERROR
}