package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class ConfirmVerificationCodeResponse constructor(
    @SerializedName("message") val message: String
)

