package com.yologger.data.datasource.api.auth.model.email_verification_code

import com.google.gson.annotations.SerializedName

enum class EmailVerificationCodeFailureResponseCode {
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,

    @SerializedName("AUTH_000") MEMBER_ALREADY_EXIST,
    @SerializedName("AUTH_001") MAIL_SYSTEM_ERROR,
}