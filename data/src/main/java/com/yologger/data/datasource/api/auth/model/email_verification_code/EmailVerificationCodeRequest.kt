package com.yologger.data.datasource.api.auth.model.email_verification_code

import com.google.gson.annotations.SerializedName

data class EmailVerificationCodeRequest
constructor(
    @SerializedName("email") val email: String
)