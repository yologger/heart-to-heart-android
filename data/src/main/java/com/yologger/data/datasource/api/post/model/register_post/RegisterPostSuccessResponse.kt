package com.yologger.data.datasource.api.post.model.register_post

import com.google.gson.annotations.SerializedName

data class RegisterPostSuccessResponse constructor(
    @SerializedName("writer_id") val writerId: Long,
    @SerializedName("post_id") val postId: Long,
    @SerializedName("content") val content: String,
    @SerializedName("image_urls") val imageUrls: List<String>? = null
)