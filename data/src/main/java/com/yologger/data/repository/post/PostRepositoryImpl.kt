package com.yologger.data.repository.post

import android.net.Uri
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.yologger.data.datasource.api.auth.model.verify_access_token.VerifyAccessTokenFailureResponse
import com.yologger.data.datasource.api.post.PostService
import com.yologger.data.datasource.api.post.model.get_posts.GetPostsFailureCode
import com.yologger.data.datasource.api.post.model.get_posts.GetPostsFailureResponse
import com.yologger.data.datasource.api.post.model.register_post.RegisterPostFailureCode
import com.yologger.data.datasource.api.post.model.register_post.RegisterPostFailureResponse
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import com.yologger.domain.repository.PostRepository
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
                val response = postService.registerPost(memberId = memberIdBody, content = contentBody, images = imagesBody).execute()
                if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    val data = RegisterPostResultData(writerId = successResponse.writerId, postId = successResponse.postId, content = successResponse.content, imageUrls = successResponse.imageUrls)
                    return RegisterPostResult.SUCCESS(data)
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), RegisterPostFailureResponse::class.java)
                    return when(failureResponse.code) {
                        RegisterPostFailureCode.INVALID_REFRESH_TOKEN, RegisterPostFailureCode.EXPIRED_REFRESH_TOKEN, RegisterPostFailureCode.MEMBER_NOT_EXIST -> {
                            RegisterPostResult.FAILURE(RegisterPostResultError.NO_SESSION)
                        }
                        RegisterPostFailureCode.NOT_FOUND, RegisterPostFailureCode.INVALID_INPUT_VALUE, RegisterPostFailureCode.ILLEGAL_STATE-> {
                            RegisterPostResult.FAILURE(RegisterPostResultError.CLIENT_ERROR)
                        }
                        RegisterPostFailureCode.FILE_UPLOAD_ERROR -> {
                            RegisterPostResult.FAILURE(RegisterPostResultError.FILE_UPLOAD_ERROR)
                        }
                        else -> {
                            RegisterPostResult.FAILURE(RegisterPostResultError.CLIENT_ERROR)
                        }
                    }
                }
            } catch (e: Exception) {
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
                    GetPostsFailureCode.NOT_FOUND -> GetPostsResult.Failure(error = GetPostsResultError.CLIENT_ERROR)
                    GetPostsFailureCode.BEARER_NOT_INCLUDED -> GetPostsResult.Failure(error = GetPostsResultError.CLIENT_ERROR)
                    GetPostsFailureCode.ILLEGAL_STATE -> GetPostsResult.Failure(error = GetPostsResultError.CLIENT_ERROR)
                    GetPostsFailureCode.INVALID_INPUT_VALUE -> GetPostsResult.Failure(error = GetPostsResultError.CLIENT_ERROR)
                    GetPostsFailureCode.MISSING_AUTHORIZATION_HEADER -> GetPostsResult.Failure(error = GetPostsResultError.CLIENT_ERROR)
                }
            }
        } catch (e: Exception) {
            Logger.w("error: ${e.localizedMessage}")
            return GetPostsResult.Failure(error = GetPostsResultError.NETWORK_ERROR)
        }
    }
}