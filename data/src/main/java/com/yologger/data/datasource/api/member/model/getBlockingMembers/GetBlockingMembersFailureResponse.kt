package com.yologger.data.datasource.api.member.model.getBlockingMembers

import com.google.gson.annotations.SerializedName

data class GetBlockingMembersFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: GetBlockingMembersFailureResponseCode,
    @SerializedName("status") val status: Int
)