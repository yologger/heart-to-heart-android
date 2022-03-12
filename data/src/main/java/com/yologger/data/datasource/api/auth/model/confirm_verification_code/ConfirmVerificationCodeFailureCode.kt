package com.yologger.data.datasource.api.auth.model.confirm_verification_code

import com.google.gson.annotations.SerializedName

enum class ConfirmVerificationCodeFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("AUTH002") INVALID_EMAIL,
    @SerializedName("AUTH003") INVALID_VERIFICATION_CODE,
    @SerializedName("AUTH004") EXPIRED_VERIFICATION_CODE,
}