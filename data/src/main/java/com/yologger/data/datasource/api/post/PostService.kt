package com.yologger.data.datasource.api.post

import com.yologger.data.datasource.api.post.model.deletePost.DeletePostSuccessResponse
import com.yologger.data.datasource.api.post.model.getAllPosts.GetAllPostsSuccessResponse
import com.yologger.data.datasource.api.post.model.registerPost.RegisterPostSuccessResponse
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
    fun getAllPosts(
        @Query("member_id") memberId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<GetAllPostsSuccessResponse>

    @GET("/post/posts/{id}")
    fun getPosts(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<GetAllPostsSuccessResponse>

    @DELETE("/post/delete/{id}")
    fun deletePost(@Path("id") id: Long): Call<DeletePostSuccessResponse>
}