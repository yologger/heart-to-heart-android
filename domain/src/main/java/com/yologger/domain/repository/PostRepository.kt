package com.yologger.domain.repository

import android.net.Uri
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResult
import com.yologger.domain.usecase.post.get_posts.GetPostsResult
import com.yologger.domain.usecase.post.register_post.RegisterPostResult

interface PostRepository {
    fun registerPost(content: String, imageUris: List<Uri>): RegisterPostResult
    fun getPosts(page: Int, size: Int): GetPostsResult
    fun fetchMemberInfo(): FetchMemberInfoResult
}