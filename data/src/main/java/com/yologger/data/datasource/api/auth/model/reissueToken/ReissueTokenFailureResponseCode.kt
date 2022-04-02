package com.yologger.data.datasource.api.auth.model.reissueToken

import com.google.gson.annotations.SerializedName

enum class ReissueTokenFailureResponseCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("AUTH_005") MEMBER_NOT_EXIST,
    @SerializedName("AUTH_007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH_008") EXPIRED_REFRESH_TOKEN,
}