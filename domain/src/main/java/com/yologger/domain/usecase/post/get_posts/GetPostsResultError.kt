package com.yologger.domain.usecase.post.get_posts

enum class GetPostsResultError {
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION
}