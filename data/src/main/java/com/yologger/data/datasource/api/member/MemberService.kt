package com.yologger.data.datasource.api.member

import com.yologger.data.datasource.api.member.model.get_member_profile.GetMemberProfileSuccessResponse
import com.yologger.data.datasource.api.member.model.update_avatar.UpdateAvatarSuccessResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface MemberService {
    @Multipart
    @POST("/member/uploadAvatar")
    fun updateAvatar(
        @Part("member_id") memberId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UpdateAvatarSuccessResponse>

    @GET("/member/profile")
    fun getMemberProfile(
        @Query("member_id") memberId: Long
    ): Call<GetMemberProfileSuccessResponse>
}