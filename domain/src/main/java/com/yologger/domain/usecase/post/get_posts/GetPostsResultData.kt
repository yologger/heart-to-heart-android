package com.yologger.domain.usecase.post.get_posts

data class GetPostsResultData(
    val size: Int,
    val posts: List<PostData>
)