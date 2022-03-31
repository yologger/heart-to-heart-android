package com.yologger.data.datasource.api.post

import com.yologger.data.datasource.api.post.model.get_posts.GetPostsSuccessResponse
import com.yologger.data.datasource.api.post.model.register_post.RegisterPostSuccessResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostService {

    @Multipart
    @POST("/post/registerPost")
    fun registerPost(
        @Part("member_id") memberId: RequestBody,
        @Part("content") content: RequestBody,
        @Part images: List<MultipartBody.Part>?
    ): Call<RegisterPostSuccessResponse>

    @GET("/post/posts")
    fun getPosts(
        @Query("member_id") memberId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<GetPostsSuccessResponse>
}