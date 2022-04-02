package com.yologger.domain.usecase.post.getAllPosts

enum class GetAllPostsResultError {
    // Common Error
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    JSON_PARSE_ERROR,

    // Business Error
    NO_POSTS_EXIST
}