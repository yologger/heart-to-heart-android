package com.yologger.data.datasource.api.member.model.block

import com.google.gson.annotations.SerializedName

data class BlockMemberSuccessResponse(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("avatar_url") val avatarUrl: String
)