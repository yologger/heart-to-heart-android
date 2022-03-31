package com.yologger.data.datasource.api.member.model.get_member_profile

import com.google.gson.annotations.SerializedName

data class GetMemberProfileFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: GetMemberProfileFailureResponseCode,
    @SerializedName("status") val status: Int
)