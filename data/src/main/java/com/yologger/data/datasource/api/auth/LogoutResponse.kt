package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: LogoutFailureCode,
    @SerializedName("status") val status: Int
)