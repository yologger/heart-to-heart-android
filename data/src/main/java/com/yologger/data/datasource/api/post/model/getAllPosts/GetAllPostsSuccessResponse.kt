package com.yologger.data.datasource.api.post.model.getAllPosts

import com.google.gson.annotations.SerializedName

data class GetAllPostsSuccessResponse constructor(
    @SerializedName("size") val size: Int,
    @SerializedName("posts") val posts: List<Post>
)