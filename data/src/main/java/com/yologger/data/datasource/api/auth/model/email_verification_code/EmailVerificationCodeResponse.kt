package com.yologger.data.datasource.api.auth.model.email_verification_code

import com.google.gson.annotations.SerializedName

data class EmailVerificationCodeResponse constructor(
    @SerializedName("message") val message: String
)