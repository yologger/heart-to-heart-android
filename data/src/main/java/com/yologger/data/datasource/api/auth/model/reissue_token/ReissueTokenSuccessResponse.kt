package com.yologger.data.datasource.api.auth.model.reissue_token

import com.google.gson.annotations.SerializedName

data class ReissueTokenSuccessResponse constructor(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("email") val email: String,
)