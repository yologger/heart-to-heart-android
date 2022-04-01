package com.yologger.data.datasource.api.post.model.registerPost

import com.google.gson.annotations.SerializedName

data class RegisterPostSuccessResponse constructor(
    @SerializedName("post_id") val postId: Long,
    @SerializedName("writer_id") val writerId: Long,
    @SerializedName("writer_email") val writerEmail: String,
    @SerializedName("writer_nickname") val writerNickname: String,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("content") val content: String,
    @SerializedName("image_urls") val imageUrls: List<String>? = null,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)