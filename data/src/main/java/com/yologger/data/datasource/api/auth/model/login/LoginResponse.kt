package com.yologger.data.datasource.api.auth.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse constructor(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String
)