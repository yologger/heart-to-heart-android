package com.yologger.domain.usecase.post.get_posts

enum class GetPostsResultError {
    // Common Error
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    JSON_PARSE_ERROR,

    // Business Error
    NO_POST_EXIST
}