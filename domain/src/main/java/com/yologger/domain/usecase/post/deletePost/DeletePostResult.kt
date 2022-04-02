package com.yologger.domain.usecase.post.deletePost

sealed class DeletePostResult {
    object Success: DeletePostResult()
    data class Failure(val error: DeletePostResultError): DeletePostResult()
}