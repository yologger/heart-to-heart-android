package com.yologger.data.datasource.api.auth.model.verifyAccessToken

import com.google.gson.annotations.SerializedName

data class VerifyAccessTokenFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: VerifyAccessTokenFailureResponseCode,
    @SerializedName("status") val status: Int
)