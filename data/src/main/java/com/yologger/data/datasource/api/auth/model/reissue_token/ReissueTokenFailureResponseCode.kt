package com.yologger.data.datasource.api.auth.model.reissue_token

import com.google.gson.annotations.SerializedName

enum class ReissueTokenFailureResponseCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("AUTH006") MEMBER_NOT_EXIST,
    @SerializedName("AUTH007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH008") EXPIRED_REFRESH_TOKEN,
}