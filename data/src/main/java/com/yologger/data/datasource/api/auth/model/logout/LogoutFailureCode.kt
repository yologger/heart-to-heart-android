package com.yologger.data.datasource.api.auth.model.logout

import com.google.gson.annotations.SerializedName

enum class LogoutFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL005") MISSING_REQUEST_HEADER,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("AUTH009") INVALID_ACCESS_TOKEN,
    @SerializedName("AUTH010") EXPIRED_ACCESS_TOKEN,
    @SerializedName("AUTH011") BEARER_NOT_INCLUDED,
}