package com.yologger.domain.repository

import android.net.Uri
import com.yologger.domain.usecase.post.deletePost.DeletePostResult
import com.yologger.domain.usecase.post.getAllPosts.GetAllPostsResult
import com.yologger.domain.usecase.post.getPosts.GetPostsResult
import com.yologger.domain.usecase.post.registerPost.RegisterPostResult

interface PostRepository {
    fun registerPost(content: String, imageUris: List<Uri>): RegisterPostResult
    fun getAllPosts(page: Int, size: Int): GetAllPostsResult
    fun getPosts(id: Long?, page: Int, size: Int): GetPostsResult
    fun deletePost(id: Long): DeletePostResult
}