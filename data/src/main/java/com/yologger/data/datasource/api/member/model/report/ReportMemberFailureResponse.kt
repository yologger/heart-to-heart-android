package com.yologger.data.datasource.api.member.model.report

import com.google.gson.annotations.SerializedName

data class ReportMemberFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: ReportMemberFailureResponseCode,
    @SerializedName("status") val status: Int
)