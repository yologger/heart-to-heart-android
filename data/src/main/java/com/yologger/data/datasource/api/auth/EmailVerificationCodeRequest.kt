package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class EmailVerificationCodeRequest
constructor(
    @SerializedName("email") val email: String
)