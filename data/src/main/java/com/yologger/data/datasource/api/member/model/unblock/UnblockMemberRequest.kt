package com.yologger.data.datasource.api.member.model.unblock

import com.google.gson.annotations.SerializedName

data class UnblockMemberRequest(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("target_id") val targetId: Long
)