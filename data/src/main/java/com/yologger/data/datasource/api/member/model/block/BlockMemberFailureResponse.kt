package com.yologger.data.datasource.api.member.model.block

import com.google.gson.annotations.SerializedName

data class BlockMemberFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: BlockMemberFailureResponseCode,
    @SerializedName("status") val status: Int
)