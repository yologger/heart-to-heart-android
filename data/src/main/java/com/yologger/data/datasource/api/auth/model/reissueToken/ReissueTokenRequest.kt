package com.yologger.data.datasource.api.auth.model.reissueToken

import com.google.gson.annotations.SerializedName

data class ReissueTokenRequest
constructor(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("refresh_token") val refreshToken: String
)