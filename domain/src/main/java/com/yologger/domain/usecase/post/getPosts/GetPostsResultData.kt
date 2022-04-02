package com.yologger.domain.usecase.post.getPosts

data class GetPostsResultData(
    val size: Int,
    val posts: List<PostData>
)