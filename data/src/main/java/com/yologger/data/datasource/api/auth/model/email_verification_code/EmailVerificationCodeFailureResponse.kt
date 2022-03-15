package com.yologger.data.datasource.api.auth.model.email_verification_code

import com.google.gson.annotations.SerializedName

data class  EmailVerificationCodeFailureResponse constructor (
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: EmailVerificationCodeFailureCode,
    @SerializedName("status") val status: Int
)