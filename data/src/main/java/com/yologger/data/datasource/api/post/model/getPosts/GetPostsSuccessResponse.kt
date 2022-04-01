package com.yologger.data.datasource.api.post.model.getPosts

import com.google.gson.annotations.SerializedName

data class GetPostsSuccessResponse constructor(
    @SerializedName("size") val size: Int,
    @SerializedName("posts") val posts: List<Post>
)