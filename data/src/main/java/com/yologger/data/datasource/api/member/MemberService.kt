package com.yologger.data.datasource.api.member

import com.yologger.data.datasource.api.member.update_avatar.UpdateAvatarSuccessResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MemberService {
    @Multipart
    @POST("/member/uploadAvatar")
    fun updateAvatar(
        @Part("member_id") memberId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UpdateAvatarSuccessResponse>
}