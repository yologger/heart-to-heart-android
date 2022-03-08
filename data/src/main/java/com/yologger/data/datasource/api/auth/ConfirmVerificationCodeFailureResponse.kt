package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class  ConfirmVerificationCodeFailureResponse constructor (
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: ConfirmVerificationCodeFailureCode,
    @SerializedName("status") val status: Int
)