package com.yologger.domain.usecase.post.getAllPosts

sealed class GetAllPostsResult {
    data class Success(val data: GetAllPostsResultData): GetAllPostsResult()
    data class Failure(val error: GetAllPostsResultError): GetAllPostsResult()
}