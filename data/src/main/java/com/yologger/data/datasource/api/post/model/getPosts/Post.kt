package com.yologger.data.datasource.api.post.model.getPosts

import com.google.gson.annotations.SerializedName
import com.yologger.domain.usecase.post.getPosts.PostData

data class Post constructor(
    @SerializedName("id") val id: Long,
    @SerializedName("writer_id") val writerId: Long,
    @SerializedName("writer_email") val writerEmail: String,
    @SerializedName("writer_nickname") val writerNickname: String,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("content") val content: String,
    @SerializedName("image_urls") val imageUrls: List<String>? = null,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
) {
    fun toData(): PostData {
        return PostData(
            id = this.id,
            writerId = this.writerId,
            writerEmail = this.writerEmail,
            writerNickname = this.writerNickname,
            avatarUrl = this.avatarUrl,
            content = this.content,
            imageUrls = this.imageUrls,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}