package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

enum class JoinFailureCode {
    @SerializedName("G001") NOT_FOUND,
    @SerializedName("G002") INVALID_INPUT_VALUE,
    @SerializedName("G003") HTTP_REQUEST_METHOD_NOT_SUPPORTED,
    @SerializedName("A001") MEMBER_ALREADY_EXISTS,
}