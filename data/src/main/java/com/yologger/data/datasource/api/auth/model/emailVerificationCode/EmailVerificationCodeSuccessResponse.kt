package com.yologger.data.datasource.api.auth.model.emailVerificationCode

import com.google.gson.annotations.SerializedName

data class EmailVerificationCodeSuccessResponse constructor(
    @SerializedName("message") val message: String
)