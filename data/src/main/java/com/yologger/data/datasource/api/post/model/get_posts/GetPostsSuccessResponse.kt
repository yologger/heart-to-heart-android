package com.yologger.data.datasource.api.post.model.get_posts

import com.google.gson.annotations.SerializedName

data class GetPostsSuccessResponse constructor(
    @SerializedName("size") val size: Int,
    @SerializedName("posts") val posts: List<Post>
)