package com.yologger.domain.usecase.post.getPosts

import com.yologger.domain.usecase.post.getAllPosts.PostData

data class GetPostsResultData(
    val size: Int,
    val posts: List<PostData>
)