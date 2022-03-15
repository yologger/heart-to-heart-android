package com.yologger.domain.usecase.post.register_post

data class RegisterPostResultData constructor(
    val writerId: Long,
    val postId: Long,
    val content: String,
    val imageUrls: List<String>
)