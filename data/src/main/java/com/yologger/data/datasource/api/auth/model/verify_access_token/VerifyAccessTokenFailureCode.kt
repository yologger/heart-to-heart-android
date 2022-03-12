package com.yologger.data.datasource.api.auth.model.verify_access_token

import com.google.gson.annotations.SerializedName

enum class VerifyAccessTokenFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("GLOBAL100") MISSING_AUTHORIZATION_HEADER,
    @SerializedName("GLOBAL101") BEARER_NOT_INCLUDED,
    @SerializedName("GLOBAL102") ACCESS_TOKEN_EMPTY,
    @SerializedName("GLOBAL103") INVALID_ACCESS_TOKEN,
    @SerializedName("GLOBAL104") EXPIRED_ACCESS_TOKEN
}