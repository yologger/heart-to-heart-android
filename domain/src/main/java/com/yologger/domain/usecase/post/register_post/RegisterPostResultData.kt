package com.yologger.domain.usecase.post.register_post

data class RegisterPostResultData constructor(
    val postId: Long,
    val writerId: Long,
    val writerEmail: String,
    val writerNickname: String,
    val avatarUrl: String? = null,
    val content: String,
    val imageUrls: List<String>? = null,
    val createdAt: String,
    val updatedAt: String
)