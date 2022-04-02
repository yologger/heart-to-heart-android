package com.yologger.data.datasource.api.auth.model.confirmVerificationCode

import com.google.gson.annotations.SerializedName

data class ConfirmVerificationCodeSuccessResponse constructor(
    @SerializedName("message") val message: String
)

