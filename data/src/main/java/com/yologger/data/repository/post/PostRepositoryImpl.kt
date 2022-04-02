package com.yologger.data.repository.post

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.yologger.data.datasource.api.post.PostService
import com.yologger.data.datasource.api.post.model.getPosts.GetPostsFailureResponse
import com.yologger.data.datasource.api.post.model.getPosts.GetPostsFailureResponseCode
import com.yologger.data.datasource.api.post.model.registerPost.RegisterPostFailureResponse
import com.yologger.data.datasource.api.post.model.registerPost.RegisterPostFailureResponseCode
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import com.yologger.domain.repository.PostRepository
import com.yologger.domain.usecase.post.getPosts.GetPostsResult
import com.yologger.domain.usecase.post.getPosts.GetPostsResultData
import com.yologger.domain.usecase.post.getPosts.GetPostsResultError
import com.yologger.domain.usecase.post.registerPost.RegisterPostResult
import com.yologger.domain.usecase.post.registerPost.RegisterPostResultData
import com.yologger.domain.usecase.post.registerPost.RegisterPostResultError
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val gson: Gson,
    private val sessionStore: SessionStore,
    private val fileUtil: FileUtil
) : PostRepository {

    override fun registerPost(content: String, imageUris: List<Uri>) : RegisterPostResult {

        sessionStore.getSession()?.let {
            val imagesBody = imageUris.map { uri: Uri -> fileUtil.getMultipart("files", uri)!! }
            val memberIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), it.memberId.toString())
            val contentBody = RequestBody.create(MediaType.parse("multipart/form-data"), content)
            try {
                val response = postService.registerPost(
                    memberId = memberIdBody,
                    content = contentBody,
                    images = imagesBody
                ).execute()
                if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    val data = RegisterPostResultData(
                        postId = successResponse.postId,
                        writerId = successResponse.writerId,
                        writerEmail = successResponse.writerEmail,
                        writerNickname = successResponse.writerNickname,
                        avatarUrl = successResponse.avatarUrl,
                        content = successResponse.content,
                        imageUrls = successResponse.imageUrls,
                        createdAt = successResponse.createdAt,
                        updatedAt = successResponse.updatedAt
                    )
                    return RegisterPostResult.SUCCESS(data)
                } else {
                    val failureResponse = gson.fromJson(
                        response.errorBody()!!.string(),
                        RegisterPostFailureResponse::class.java
                    )
                    return when (failureResponse.code) {
                        RegisterPostFailureResponseCode.FILE_SIZE_LIMIT_EXCEEDED -> RegisterPostResult.FAILURE(
                            RegisterPostResultError.FILE_SIZE_LIMIT_EXCEEDED
                        )
                        RegisterPostFailureResponseCode.FILE_UPLOAD_ERROR -> RegisterPostResult.FAILURE(
                            RegisterPostResultError.FILE_UPLOAD_ERROR
                        )
                        RegisterPostFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, RegisterPostFailureResponseCode.INVALID_REFRESH_TOKEN, RegisterPostFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            RegisterPostResult.FAILURE(RegisterPostResultError.NO_SESSION)
                        }
                        else -> RegisterPostResult.FAILURE(RegisterPostResultError.CLIENT_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                e.printStackTrace()
                return RegisterPostResult.FAILURE(RegisterPostResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return RegisterPostResult.FAILURE(RegisterPostResultError.NETWORK_ERROR)
            }
        } ?: run {
            return RegisterPostResult.FAILURE(RegisterPostResultError.NO_SESSION)
        }
    }

    override fun getPosts(page: Int, size: Int): GetPostsResult {
        sessionStore.getSession()?.let { session ->
            try {
                val response = postService.getPosts(session.memberId, page, size).execute()
                if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    val postDataList = successResponse.posts.map { it.toData() }
                    val data = GetPostsResultData(size = successResponse.size, posts = postDataList)
                    return GetPostsResult.Success(data = data)
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), GetPostsFailureResponse::class.java)
                    return when (failureResponse.code) {
                        GetPostsFailureResponseCode.NO_POSTS_EXIST -> GetPostsResult.Failure(GetPostsResultError.NO_POSTS_EXIST)
                        GetPostsFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, GetPostsFailureResponseCode.INVALID_REFRESH_TOKEN, GetPostsFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            GetPostsResult.Failure(GetPostsResultError.NO_SESSION)
                        }
                        else -> GetPostsResult.Failure(GetPostsResultError.CLIENT_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                return GetPostsResult.Failure(error = GetPostsResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                return GetPostsResult.Failure(error = GetPostsResultError.NETWORK_ERROR)
            }
        } ?: run {
            return GetPostsResult.Failure(GetPostsResultError.NO_SESSION)
        }
    }
}