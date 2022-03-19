package com.yologger.data.datasource.api.auth.model.join

import com.google.gson.annotations.SerializedName

data class JoinFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: JoinFailureResponseCode,
    @SerializedName("status") val status: Int
)