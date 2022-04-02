package com.yologger.data.datasource.api.post.model.getPosts

import com.google.gson.annotations.SerializedName
import com.yologger.data.datasource.api.post.model.getAllPosts.Post

data class GetPostsSuccessResponse constructor(
    @SerializedName("size") val size: Int,
    @SerializedName("posts") val posts: List<Post>
)