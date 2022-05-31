package com.yologger.data.datasource.api.auth.model.logout

import com.google.gson.annotations.SerializedName

enum class LogoutFailureResponseCode {
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_005") MISSING_REQUEST_HEADER,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,

    @SerializedName("AUTH_009") BEARER_NOT_INCLUDED,
    @SerializedName("AUTH_010") INVALID_ACCESS_TOKEN,
    @SerializedName("GLOBAL_104") EXPIRED_ACCESS_TOKEN,
}