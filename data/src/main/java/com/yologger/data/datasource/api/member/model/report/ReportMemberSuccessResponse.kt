package com.yologger.data.datasource.api.member.model.report

import com.google.gson.annotations.SerializedName

data class ReportMemberSuccessResponse(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("target_id") val targetId: Long
)