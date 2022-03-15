package com.yologger.domain.repository

import android.net.Uri
import com.yologger.domain.usecase.post.register_post.RegisterPostResult

interface PostRepository {
    fun registerPost(content: String, imageUris: List<Uri>): RegisterPostResult
}