package com.yologger.data.repository.post

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.yologger.data.datasource.api.post.PostService
import com.yologger.data.datasource.api.post.model.get_posts.GetPostsFailureResponse
import com.yologger.data.datasource.api.post.model.get_posts.GetPostsFailureResponseCode
import com.yologger.data.datasource.api.post.model.register_post.RegisterPostFailureResponse
import com.yologger.data.datasource.api.post.model.register_post.RegisterPostFailureResponseCode
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import com.yologger.domain.repository.PostRepository
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResult
import com.yologger.domain.usecase.post.get_posts.GetPostsResult
import com.yologger.domain.usecase.post.get_posts.GetPostsResultData
import com.yologger.domain.usecase.post.get_posts.GetPostsResultError
import com.yologger.domain.usecase.post.register_post.RegisterPostResult
import com.yologger.domain.usecase.post.register_post.RegisterPostResultData
import com.yologger.domain.usecase.post.register_post.RegisterPostResultError
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
        try {
            val response = postService.getPosts(page, size).execute()
            if (response.isSuccessful) {
                val successResponse = response.body()!!
                val postDataList = successResponse.posts.map { it.toData() }
                val data = GetPostsResultData(size = successResponse.size, posts = postDataList)
                return GetPostsResult.Success(data = data)
            } else {
                val failureResponse = gson.fromJson(response.errorBody()!!.string(), GetPostsFailureResponse::class.java)
                return when (failureResponse.code) {
                    GetPostsFailureResponseCode.NO_POST_EXIST -> GetPostsResult.Failure(GetPostsResultError.NO_POST_EXIST)
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
    }

    override fun fetchMemberInfo(): FetchMemberInfoResult {
        sessionStore.getSession()?.let { session ->
            return FetchMemberInfoResult.Success(
                email = session.email,
                nickname = session.nickname,
                avatarUrl = session.avatarUrl
            )
        } ?: run {
            return FetchMemberInfoResult.Failure
        }
    }
}