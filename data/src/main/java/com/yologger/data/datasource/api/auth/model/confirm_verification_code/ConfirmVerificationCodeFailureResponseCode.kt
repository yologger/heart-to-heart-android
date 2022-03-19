package com.yologger.data.datasource.api.auth.model.confirm_verification_code

import com.google.gson.annotations.SerializedName

enum class ConfirmVerificationCodeFailureResponseCode {
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,

    @SerializedName("AUTH_002") INVALID_EMAIL,
    @SerializedName("AUTH_003") INVALID_VERIFICATION_CODE,
    @SerializedName("AUTH_004") EXPIRED_VERIFICATION_CODE,
}