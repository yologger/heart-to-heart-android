package com.yologger.domain.usecase.post.getAllPosts

data class GetAllPostsResultData(
    val size: Int,
    val posts: List<PostData>
)