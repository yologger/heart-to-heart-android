package com.yologger.data.datasource.api.member

import com.yologger.data.datasource.api.member.model.block.BlockMemberRequest
import com.yologger.data.datasource.api.member.model.block.BlockMemberSuccessResponse
import com.yologger.data.datasource.api.member.model.deleteAccount.DeleteAccountSuccessResponse
import com.yologger.data.datasource.api.member.model.getBlockingMembers.GetBlockingMembersSuccessResponse
import com.yologger.data.datasource.api.member.model.getMemberProfile.GetMemberProfileSuccessResponse
import com.yologger.data.datasource.api.member.model.report.ReportMemberRequest
import com.yologger.data.datasource.api.member.model.report.ReportMemberSuccessResponse
import com.yologger.data.datasource.api.member.model.unblock.UnblockMemberRequest
import com.yologger.data.datasource.api.member.model.unblock.UnblockMemberSuccessResponse
import com.yologger.data.datasource.api.member.model.updateAvatar.UpdateAvatarSuccessResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface MemberService {
    @Multipart
    @POST("member/uploadAvatar")
    fun updateAvatar(
        @Part("member_id") memberId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UpdateAvatarSuccessResponse>

    @GET("member/profile")
    fun getMemberProfile(
        @Query("member_id") memberId: Long
    ): Call<GetMemberProfileSuccessResponse>

    @POST("member/block")
    fun blockMember(@Body request: BlockMemberRequest): Call<BlockMemberSuccessResponse>

    @GET("member/getBlockingMembers")
    fun getBlockingMembers(@Query("member_id") memberId: Long): Call<GetBlockingMembersSuccessResponse>

    @POST("member/unblock")
    fun unblockMember(@Body request: UnblockMemberRequest): Call<UnblockMemberSuccessResponse>

    @POST("member/report")
    fun report(@Body request: ReportMemberRequest): Call<ReportMemberSuccessResponse>

    @DELETE("member/delete/{id}")
    fun deleteAccount(@Path("id") id: Long): Call<DeleteAccountSuccessResponse>
}