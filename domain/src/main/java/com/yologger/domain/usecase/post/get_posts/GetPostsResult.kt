package com.yologger.domain.usecase.post.get_posts

sealed class GetPostsResult {
    data class Success(val data: GetPostsResultData): GetPostsResult()
    data class Failure(val error: GetPostsResultError): GetPostsResult()
}