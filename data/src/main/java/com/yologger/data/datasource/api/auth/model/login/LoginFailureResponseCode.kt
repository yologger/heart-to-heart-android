package com.yologger.data.datasource.api.auth.model.login

import com.google.gson.annotations.SerializedName

enum class LoginFailureResponseCode {
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,

    @SerializedName("AUTH_005") MEMBER_NOT_EXIST,
    @SerializedName("AUTH_006") INVALID_PASSWORD,
}