package com.yologger.data.datasource.api.auth.model.confirm_verification_code

import com.google.gson.annotations.SerializedName

data class  ConfirmVerificationCodeFailureResponse constructor (
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: ConfirmVerificationCodeFailureResponseCode,
    @SerializedName("status") val status: Int
)