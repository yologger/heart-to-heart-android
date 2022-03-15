package com.yologger.data.datasource.api.post.model.get_posts

import com.google.gson.annotations.SerializedName
import com.yologger.data.datasource.api.auth.model.verify_access_token.VerifyAccessTokenFailureCode

data class GetPostsFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: GetPostsFailureCode,
    @SerializedName("status") val status: Int)
