package com.yologger.data.datasource.api.member.model.unblock

import com.google.gson.annotations.SerializedName

data class UnblockMemberSuccessResponse(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("avatar_url") val avatarUrl: String
)