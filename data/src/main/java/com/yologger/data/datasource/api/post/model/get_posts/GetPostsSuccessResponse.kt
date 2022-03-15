package com.yologger.data.datasource.api.post.model.get_posts

import com.google.gson.annotations.SerializedName

data class GetPostsSuccessResponse constructor(
    @SerializedName("data") val data: String,
)