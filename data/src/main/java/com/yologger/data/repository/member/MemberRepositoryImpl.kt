package com.yologger.data.repository.member

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import com.yologger.data.datasource.api.member.MemberService
import com.yologger.data.datasource.api.member.model.block.BlockMemberFailureResponse
import com.yologger.data.datasource.api.member.model.block.BlockMemberFailureResponseCode
import com.yologger.data.datasource.api.member.model.block.BlockMemberRequest
import com.yologger.data.datasource.api.member.model.deleteAccount.DeleteAccountFailureResponse
import com.yologger.data.datasource.api.member.model.deleteAccount.DeleteAccountFailureResponseCode
import com.yologger.data.datasource.api.member.model.getBlockingMembers.GetBlockingMembersFailureResponse
import com.yologger.data.datasource.api.member.model.getBlockingMembers.GetBlockingMembersFailureResponseCode
import com.yologger.data.datasource.api.member.model.getMemberProfile.GetMemberProfileFailureResponse
import com.yologger.data.datasource.api.member.model.getMemberProfile.GetMemberProfileFailureResponseCode
import com.yologger.data.datasource.api.member.model.report.ReportMemberFailureResponse
import com.yologger.data.datasource.api.member.model.report.ReportMemberFailureResponseCode
import com.yologger.data.datasource.api.member.model.report.ReportMemberRequest
import com.yologger.data.datasource.api.member.model.unblock.UnblockMemberFailureResponse
import com.yologger.data.datasource.api.member.model.unblock.UnblockMemberFailureResponseCode
import com.yologger.data.datasource.api.member.model.unblock.UnblockMemberRequest
import com.yologger.data.datasource.api.member.model.updateAvatar.UpdateAvatarFailureResponse
import com.yologger.data.datasource.api.member.model.updateAvatar.UpdateAvatarFailureResponseCode
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import com.yologger.domain.repository.MemberRepository
import com.yologger.domain.usecase.member.blockMember.BlockMemberResult
import com.yologger.domain.usecase.member.blockMember.BlockMemberResultError
import com.yologger.domain.usecase.member.deleteAccount.DeleteAccountResult
import com.yologger.domain.usecase.member.deleteAccount.DeleteAccountResultError
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResult
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResultError
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersResult
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersResultData
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersResultError
import com.yologger.domain.usecase.member.reportMember.ReportMemberResult
import com.yologger.domain.usecase.member.reportMember.ReportMemberResultError
import com.yologger.domain.usecase.member.unblockMember.UnblockMemberResult
import com.yologger.domain.usecase.member.unblockMember.UnblockMemberResultError
import com.yologger.domain.usecase.member.updateAvatar.UpdateAvatarResult
import com.yologger.domain.usecase.member.updateAvatar.UpdateAvatarResultError
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
                        id = successResponse.memberId,
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

    override fun fetchMemberInfo(memberId: Long): FetchMemberInfoResult {
        try {
            val response = memberService.getMemberProfile(memberId).execute()
            if (response.isSuccessful) {
                val successResponse = response.body()!!
                return FetchMemberInfoResult.Success(
                    id = successResponse.memberId,
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
    }

    override fun blockMember(targetId: Long): BlockMemberResult {
        sessionStore.getSession()?.let { session ->
            try {
                val request = BlockMemberRequest(memberId = session.memberId, targetId = targetId)
                val response = memberService.blockMember(request).execute()
                return if (response.isSuccessful) {
                    BlockMemberResult.Success
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), BlockMemberFailureResponse::class.java)
                    when(failureResponse.code) {
                        BlockMemberFailureResponseCode.ALREADY_BLOCKING -> BlockMemberResult.Failure(BlockMemberResultError.ALREADY_BLOCKING)
                        BlockMemberFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, BlockMemberFailureResponseCode.INVALID_REFRESH_TOKEN, BlockMemberFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            BlockMemberResult.Failure(BlockMemberResultError.NO_SESSION)
                        }
                        else -> BlockMemberResult.Failure(BlockMemberResultError.NETWORK_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                e.printStackTrace()
                return BlockMemberResult.Failure(BlockMemberResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return BlockMemberResult.Failure(BlockMemberResultError.NETWORK_ERROR)
            }
        } ?: run {
            return BlockMemberResult.Failure(BlockMemberResultError.NO_SESSION)
        }
    }

    override fun reportMember(targetId: Long): ReportMemberResult {
        sessionStore.getSession()?.let { session ->
            try {
                val request = ReportMemberRequest(memberId = session.memberId, targetId = targetId)
                val response = memberService.report(request).execute()
                return if (response.isSuccessful) {
                    ReportMemberResult.Success
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), ReportMemberFailureResponse::class.java)
                    when (failureResponse.code) {
                        ReportMemberFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, ReportMemberFailureResponseCode.INVALID_REFRESH_TOKEN, ReportMemberFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            ReportMemberResult.Failure(ReportMemberResultError.NO_SESSION)
                        }
                        else -> ReportMemberResult.Failure(ReportMemberResultError.NETWORK_ERROR)
                    }
                }
            } catch  (e: JsonParseException) {
                e.printStackTrace()
                return ReportMemberResult.Failure(ReportMemberResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return ReportMemberResult.Failure(ReportMemberResultError.NETWORK_ERROR)
            }
        } ?: run {
            return ReportMemberResult.Failure(ReportMemberResultError.INVALID_PARAMS)
        }
    }

    override fun getBlockingMembers(): GetBlockingMembersResult {
        sessionStore.getSession()?.let { session ->
            try {
                val response = memberService.getBlockingMembers(session.memberId).execute()
                return if (response.isSuccessful) {
                    val successResponse = response.body()!!
                    val memberDataList = successResponse.members.map { it.toData() }
                    val data = GetBlockingMembersResultData(size = memberDataList.size, members = memberDataList)
                    GetBlockingMembersResult.Success(data = data)
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), GetBlockingMembersFailureResponse::class.java)
                    return when (failureResponse.code) {
                        GetBlockingMembersFailureResponseCode.INVALID_MEMBER_ID -> GetBlockingMembersResult.Failure(GetBlockingMembersResultError.INVALID_MEMBER_ID)
                        GetBlockingMembersFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, GetBlockingMembersFailureResponseCode.INVALID_REFRESH_TOKEN, GetBlockingMembersFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            GetBlockingMembersResult.Failure(GetBlockingMembersResultError.NO_SESSION)
                        }
                        else -> GetBlockingMembersResult.Failure(GetBlockingMembersResultError.CLIENT_ERROR)
                    }
                }
            } catch  (e: JsonParseException) {
                e.printStackTrace()
                return GetBlockingMembersResult.Failure(GetBlockingMembersResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return GetBlockingMembersResult.Failure(GetBlockingMembersResultError.NETWORK_ERROR)
            }
        } ?: run {
            return GetBlockingMembersResult.Failure(GetBlockingMembersResultError.INVALID_PARAMS)
        }
    }

    override fun unblockMember(targetId: Long): UnblockMemberResult {
        sessionStore.getSession()?.let { session ->
            try {
                val request = UnblockMemberRequest(memberId = session.memberId, targetId = targetId)
                val response = memberService.unblockMember(request).execute()
                return if (response.isSuccessful) {
                    UnblockMemberResult.Success
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), UnblockMemberFailureResponse::class.java)
                    when(failureResponse.code) {
                        UnblockMemberFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, UnblockMemberFailureResponseCode.INVALID_REFRESH_TOKEN, UnblockMemberFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            UnblockMemberResult.Failure(UnblockMemberResultError.NO_SESSION)
                        }
                        else -> UnblockMemberResult.Failure(UnblockMemberResultError.NETWORK_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                e.printStackTrace()
                return UnblockMemberResult.Failure(UnblockMemberResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return UnblockMemberResult.Failure(UnblockMemberResultError.NETWORK_ERROR)
            }
        } ?: run {
            return UnblockMemberResult.Failure(UnblockMemberResultError.NO_SESSION)
        }
    }

    override fun deleteAccount(): DeleteAccountResult {
        sessionStore.getSession()?.let { session ->
            try {
                val response = memberService.deleteAccount(session.memberId).execute()
                return if (response.isSuccessful) {
                    sessionStore.deleteSession()
                    DeleteAccountResult.Success
                } else {
                    val failureResponse = gson.fromJson(response.errorBody()!!.string(), DeleteAccountFailureResponse::class.java)
                    when(failureResponse.code) {
                        DeleteAccountFailureResponseCode.AWS_S3_ERROR -> {
                            sessionStore.deleteSession()
                            DeleteAccountResult.Failure(DeleteAccountResultError.AWS_S3_ERROR)
                        }
                        DeleteAccountFailureResponseCode.NO_ACCESS_TOKEN_IN_LOCAL, DeleteAccountFailureResponseCode.INVALID_REFRESH_TOKEN, DeleteAccountFailureResponseCode.EXPIRED_REFRESH_TOKEN -> {
                            sessionStore.deleteSession()
                            DeleteAccountResult.Failure(DeleteAccountResultError.NO_SESSION)
                        }
                        else -> DeleteAccountResult.Failure(DeleteAccountResultError.NETWORK_ERROR)
                    }
                }
            } catch (e: JsonParseException) {
                e.printStackTrace()
                return DeleteAccountResult.Failure(DeleteAccountResultError.JSON_PARSE_ERROR)
            } catch (e: Exception) {
                e.printStackTrace()
                return DeleteAccountResult.Failure(DeleteAccountResultError.NETWORK_ERROR)
            }
        } ?: run {
            return DeleteAccountResult.Failure(DeleteAccountResultError.NO_SESSION)
        }
    }
}