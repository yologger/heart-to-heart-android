package com.yologger.domain.usecase.post.get_posts

data class PostData constructor(
    val id: Long,
    val writerId: Long,
    val writerEmail: String,
    val writerNickname: String,
    val avatarUrl: String? = null,
    val content: String,
    val imageUrls: List<String>? = null,
    val createdAt: String,
    val updatedAt: String
)
