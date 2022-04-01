package com.yologger.data.datasource.api.auth.model.confirmVerificationCode

import com.google.gson.annotations.SerializedName

class ConfirmVerificationCodeRequest constructor(
    @SerializedName("email") val email: String,
    @SerializedName("verification_code") val verificationCode: String
)