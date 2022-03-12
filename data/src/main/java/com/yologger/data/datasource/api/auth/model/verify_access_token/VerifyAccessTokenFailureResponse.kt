package com.yologger.data.datasource.api.auth.model.verify_access_token

import com.google.gson.annotations.SerializedName

data class VerifyAccessTokenFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: VerifyAccessTokenFailureCode,
    @SerializedName("status") val status: Int
)