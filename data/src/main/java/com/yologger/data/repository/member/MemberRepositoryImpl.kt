package com.yologger.data.repository.member

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import com.yologger.data.datasource.api.member.MemberService
import com.yologger.data.datasource.api.member.update_avatar.UpdateAvatarFailureResponse
import com.yologger.data.datasource.api.member.update_avatar.UpdateAvatarFailureResponseCode
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import com.yologger.domain.repository.MemberRepository
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
            var memberIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), memberId.toString())
            val imageBody = fileUtil.getMultipart("file", imageUri)!!

            return try {
                val response = memberService.updateAvatar(memberId = memberIdBody, image = imageBody).execute()
                if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    sessionStore.updateAvatarUrl(successResponse.avatarUrl)
                    UpdateAvatarResult.Success(avatarUrl = successResponse.avatarUrl)
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), UpdateAvatarFailureResponse::class.java)
                    when (failureResponse.code) {
                        UpdateAvatarFailureResponseCode.FILE_UPLOAD_ERROR -> UpdateAvatarResult.Failure(UpdateAvatarResultError.FILE_UPLOAD_ERROR)
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

}