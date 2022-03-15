package com.yologger.domain.usecase.post.register_post

sealed class RegisterPostResult {
    data class SUCCESS(val data: RegisterPostResultData): RegisterPostResult()
    data class FAILURE(val error: RegisterPostResultError): RegisterPostResult()
}