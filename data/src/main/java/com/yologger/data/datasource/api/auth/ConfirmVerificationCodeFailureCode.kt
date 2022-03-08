package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

enum class ConfirmVerificationCodeFailureCode {
    @SerializedName("G001") NOT_FOUND,
    @SerializedName("A002") INVALID_EMAIL,
    @SerializedName("A004") EXPIRED_VERIFICATION_CODE,
    @SerializedName("A003") INVALID_VERIFICATION_CODE,
}