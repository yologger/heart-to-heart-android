package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse constructor(
    @SerializedName("user_id") val userId: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String
)