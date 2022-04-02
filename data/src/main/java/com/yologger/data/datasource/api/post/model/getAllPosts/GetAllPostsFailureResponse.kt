package com.yologger.data.datasource.api.post.model.getAllPosts

import com.google.gson.annotations.SerializedName

data class GetAllPostsFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: GetAllPostsFailureResponseCode,
    @SerializedName("status") val status: Int
)
