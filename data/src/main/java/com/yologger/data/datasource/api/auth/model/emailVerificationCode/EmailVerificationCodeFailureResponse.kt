package com.yologger.data.datasource.api.auth.model.emailVerificationCode

import com.google.gson.annotations.SerializedName

data class  EmailVerificationCodeFailureResponse constructor (
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: EmailVerificationCodeFailureResponseCode,
    @SerializedName("status") val status: Int
)