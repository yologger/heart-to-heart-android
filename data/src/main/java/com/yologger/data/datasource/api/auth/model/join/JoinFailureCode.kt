package com.yologger.data.datasource.api.auth.model.join

import com.google.gson.annotations.SerializedName

enum class JoinFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("AUTH001") MEMBER_ALREADY_EXIST,
}