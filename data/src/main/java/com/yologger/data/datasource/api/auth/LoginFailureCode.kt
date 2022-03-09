package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

enum class LoginFailureCode {
    @SerializedName("G001") NOT_FOUND,
    @SerializedName("G002") INVALID_INPUT_VALUE,
    @SerializedName("G003") HTTP_REQUEST_METHOD_NOT_SUPPORTED,
    @SerializedName("A005") INVALID_PASSWORD,
    @SerializedName("A006") MEMBER_NOT_EXIST,
}