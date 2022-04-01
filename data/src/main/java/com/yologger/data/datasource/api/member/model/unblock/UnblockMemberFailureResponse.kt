package com.yologger.data.datasource.api.member.model.unblock

import com.google.gson.annotations.SerializedName

data class UnblockMemberFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: UnblockMemberFailureResponseCode,
    @SerializedName("status") val status: Int
)