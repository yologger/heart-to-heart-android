package com.yologger.data.datasource.api.member.model.block

import com.google.gson.annotations.SerializedName

data class BlockMemberRequest(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("target_id") val targetId: Long
)