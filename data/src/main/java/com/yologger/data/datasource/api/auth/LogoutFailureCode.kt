package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

enum class LogoutFailureCode {
    @SerializedName("G001") NOT_FOUND,
    @SerializedName("G002") INVALID_INPUT_VALUE,
    @SerializedName("G003") HTTP_REQUEST_METHOD_NOT_SUPPORTED,
    @SerializedName("G005") AUTHORIZATION_HEADER_EMPTY,
    @SerializedName("G006") NOT_START_WITH_BEARER,
    @SerializedName("G008") INVALID_ACCESS_TOKEN,
    @SerializedName("G009") EXPIRED_ACCESS_TOKEN,
}