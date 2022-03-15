package com.yologger.data.datasource.api.auth.model.login

import com.google.gson.annotations.SerializedName

enum class LoginFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("AUTH005") INVALID_PASSWORD,
    @SerializedName("AUTH006") MEMBER_NOT_EXIST,
}