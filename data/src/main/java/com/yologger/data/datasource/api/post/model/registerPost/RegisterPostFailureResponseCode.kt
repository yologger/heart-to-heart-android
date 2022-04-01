package com.yologger.data.datasource.api.post.model.registerPost

import com.google.gson.annotations.SerializedName

enum class RegisterPostFailureResponseCode {
    // Common Error
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,
    @SerializedName("LOCAL_001") NO_ACCESS_TOKEN_IN_LOCAL,
    @SerializedName("AUTH_007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH_008") EXPIRED_REFRESH_TOKEN,

    // Business Error
    @SerializedName("GLOBAL_008") FILE_SIZE_LIMIT_EXCEEDED,
    @SerializedName("POST_000") INVALID_WRITER_ID,
    @SerializedName("POST_001") INVALID_CONTENT_TYPE,
    @SerializedName("POST_002") FILE_UPLOAD_ERROR,
}