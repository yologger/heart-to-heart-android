package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class EmailVerificationCodeResponse constructor(
    @SerializedName("message") val message: String
)