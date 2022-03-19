package com.yologger.data.datasource.api.post.model.get_posts

import com.google.gson.annotations.SerializedName

data class GetPostsFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: GetPostsFailureResponseCode,
    @SerializedName("status") val status: Int
)
