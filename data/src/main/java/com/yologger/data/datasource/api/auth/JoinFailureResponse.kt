package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class JoinFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: JoinFailureCode,
    @SerializedName("status") val status: Int
)