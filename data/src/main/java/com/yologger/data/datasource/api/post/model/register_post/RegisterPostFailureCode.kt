package com.yologger.data.datasource.api.post.model.register_post

import com.google.gson.annotations.SerializedName

enum class RegisterPostFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,
    @SerializedName("GLOBAL008") FILE_SIZE_LIMIT_EXCEEDED,

    @SerializedName("GLOBAL100") MISSING_AUTHORIZATION_HEADER,
    @SerializedName("GLOBAL101") BEARER_NOT_INCLUDED,
    @SerializedName("GLOBAL102") ACCESS_TOKEN_EMPTY,
    @SerializedName("GLOBAL103") INVALID_ACCESS_TOKEN,
    @SerializedName("GLOBAL104") EXPIRED_ACCESS_TOKEN,

    @SerializedName("AUTH007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH008") EXPIRED_REFRESH_TOKEN,
    @SerializedName("AUTH006") MEMBER_NOT_EXIST,

    @SerializedName("POST000") INVALID_WRITER_ID,
    @SerializedName("POST001") INVALID_CONTENT_TYPE,
    @SerializedName("POST002") FILE_UPLOAD_ERROR,
}