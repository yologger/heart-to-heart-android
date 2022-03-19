package com.yologger.domain.usecase.member.update_avatar

enum class UpdateAvatarResultError {
    // Common Error
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    JSON_PARSE_ERROR,

    // Business Error
    MEMBER_NOT_EXIST,
    FILE_UPLOAD_ERROR,
    IO_ERROR,
    INVALID_CONTENT_TYPE
}