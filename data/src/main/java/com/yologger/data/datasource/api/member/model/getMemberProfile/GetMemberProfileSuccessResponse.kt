package com.yologger.data.datasource.api.member.model.getMemberProfile

import com.google.gson.annotations.SerializedName

data class GetMemberProfileSuccessResponse constructor(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("post_size") val postSize: Int,
    @SerializedName("follower_size") val followerSize: Int,
    @SerializedName("following_size") val followingSize: Int,
)