package com.yologger.domain.repository

import android.net.Uri
import com.yologger.domain.usecase.post.getPosts.GetPostsResult
import com.yologger.domain.usecase.post.registerPost.RegisterPostResult

interface PostRepository {
    fun registerPost(content: String, imageUris: List<Uri>): RegisterPostResult
    fun getPosts(page: Int, size: Int): GetPostsResult
}