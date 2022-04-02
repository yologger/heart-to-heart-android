package com.yologger.data.datasource.api.member.model.deleteAccount

import com.google.gson.annotations.SerializedName

enum class DeleteAccountFailureResponseCode {
    // Common Error
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,
    @SerializedName("LOCAL_001") NO_ACCESS_TOKEN_IN_LOCAL,
    @SerializedName("AUTH_007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH_008") EXPIRED_REFRESH_TOKEN,

    // Business Error
    @SerializedName("MEMBER_005") INVALID_MEMBER_ID,
    @SerializedName("MEMBER_007") AWS_S3_ERROR,
}