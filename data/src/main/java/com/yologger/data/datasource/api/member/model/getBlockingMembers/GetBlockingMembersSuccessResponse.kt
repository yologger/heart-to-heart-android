package com.yologger.data.datasource.api.member.model.getBlockingMembers

import com.google.gson.annotations.SerializedName

data class GetBlockingMembersSuccessResponse(
    @SerializedName("size") val size: Int,
    @SerializedName("members") val members: List<Member>
)
