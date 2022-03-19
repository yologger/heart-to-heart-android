package com.yologger.data.datasource.api.auth.model.verify_access_token

import com.google.gson.annotations.SerializedName

enum class VerifyAccessTokenFailureResponseCode {
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,

    @SerializedName("GLOBAL_100") MISSING_AUTHORIZATION_HEADER,
    @SerializedName("GLOBAL_101") BEARER_NOT_INCLUDED,
    @SerializedName("GLOBAL_102") ACCESS_TOKEN_EMPTY,
    @SerializedName("GLOBAL_103") INVALID_ACCESS_TOKEN,
    @SerializedName("GLOBAL_104") EXPIRED_ACCESS_TOKEN
}