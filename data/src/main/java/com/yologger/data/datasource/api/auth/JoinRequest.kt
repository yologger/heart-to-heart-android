package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class JoinRequest constructor(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("password") val password: String
)
