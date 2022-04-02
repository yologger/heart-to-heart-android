package com.yologger.domain.usecase.post.getPosts

sealed class GetPostsResult {
    data class Success(val data: GetPostsResultData): GetPostsResult()
    data class Failure(val error: GetPostsResultError): GetPostsResult()
}