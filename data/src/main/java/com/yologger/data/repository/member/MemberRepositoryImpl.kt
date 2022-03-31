package com.yologger.data.repository.member

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import com.yologger.data.datasource.api.member.MemberService
import com.yologger.data.datasource.api.member.model.get_member_profile.GetMemberProfileFailureResponse
import com.yologger.data.datasource.api.member.model.get_member_profile.GetMemberProfileFailureResponseCode
import com.yologger.data.datasource.api.member.model.update_avatar.UpdateAvatarFailureResponse
import com.yologger.data.datasource.api.member.model.update_avatar.UpdateAvatarFailureResponseCode
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import com.yologger.domain.repository.MemberRepository
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResult
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResultError
import com.yologger.domain.usecase.member.update_avatar.UpdateAvatarResult
import com.yologger.domain.usecase.member.update_avatar.UpdateAvatarResultError
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService,
    private val gson: Gson,
    private val sessionStore: SessionStore,
    private val fileUtil: FileUtil
) : MemberRepository {

    override fun updateAvatar(imageUri: Uri): UpdateAvatarResult {
        sessionStore.getSession()?.let { session ->
            val memberId = session.memberId
            var memberIdBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), memberId.toString())
            val imageBody = fileUtil.getMultipart("file", imageUri)!!

            return try {
                val response = memberService.updateAvatar(memberId = memberIdBody, image = imageBody).execute()
                if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    sessionStore.updateAvatarUrl(successResponse.avatarUrl)
                    UpdateAvatarResult.Success(avatarUrl = successResponse.avatarUrl)
                } else {
                    val failureResponse = gson.fromJson(
                        response.errorBody()!!.string(),
                        UpdateAvatarFailureResponse::class.java
                    )
                    when (failureResponse.code) {
                        UpdateAvatarFailureResponseCode.FILE_UPLOAD_ERROR -> UpdateAvatarResult.Failure(
                            UpdateAvatarResultError.FILE_UPLOAD_ERROR
                        )
                        UpdateAvatarFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, UpdateAvatarFailureResponseCode.INVALID_REFRESH_TOKEN, UpdateAvatarFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            UpdateAvatarResult.Failure(UpdateAvatarResultError.NO_SESSION)
                        }
                        else -> UpdateAvatarResult.Failure(UpdateAvatarResultError.CLIENT_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                Logger.w(e.localizedMessage)
                UpdateAvatarResult.Failure(UpdateAvatarResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                Logger.w(e.localizedMessage)
                UpdateAvatarResult.Failure(UpdateAvatarResultError.CLIENT_ERROR)
            }
        } ?: run {
            return UpdateAvatarResult.Failure(UpdateAvatarResultError.NO_SESSION)
        }
    }

    override fun fetchMemberInfo(): FetchMemberInfoResult {
        sessionStore.getSession()?.let { session ->
            try {
                val response = memberService.getMemberProfile(session.memberId).execute()
                if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    return FetchMemberInfoResult.Success(
                        email = successResponse.email,
                        nickname = successResponse.nickname,
                        name = successResponse.name,
                        avatarUrl = successResponse.avatarUrl,
                        postSize = successResponse.postSize,
                        followerSize = successResponse.followerSize,
                        followingSize = successResponse.followingSize
                    )
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), GetMemberProfileFailureResponse::class.java)
                    return when (failureResponse.code) {
                        GetMemberProfileFailureResponseCode.INVALID_MEMBER_ID -> {
                            sessionStore.deleteSession()
                            FetchMemberInfoResult.Failure(FetchMemberInfoResultError.NO_SESSION)
                        }
                        GetMemberProfileFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, GetMemberProfileFailureResponseCode.INVALID_REFRESH_TOKEN, GetMemberProfileFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            FetchMemberInfoResult.Failure(FetchMemberInfoResultError.NO_SESSION)
                        }
                        else -> FetchMemberInfoResult.Failure(FetchMemberInfoResultError.NETWORK_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                e.printStackTrace()
                return FetchMemberInfoResult.Failure(FetchMemberInfoResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return FetchMemberInfoResult.Failure(FetchMemberInfoResultError.NETWORK_ERROR)
            }
        } ?: run {
            return FetchMemberInfoResult.Failure(FetchMemberInfoResultError.NO_SESSION)
        }
    }
}