package com.yologger.data.datasource.api.auth.model.emailVerificationCode

import com.google.gson.annotations.SerializedName

data class EmailVerificationCodeRequest
constructor(
    @SerializedName("email") val email: String
)