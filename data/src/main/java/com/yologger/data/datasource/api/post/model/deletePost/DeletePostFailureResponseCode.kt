package com.yologger.data.datasource.api.post.model.deletePost

import com.google.gson.annotations.SerializedName

enum class DeletePostFailureResponseCode {
    // Common Error
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,
    @SerializedName("LOCAL_001") NO_ACCESS_TOKEN_IN_LOCAL,
    @SerializedName("AUTH_007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH_008") EXPIRED_REFRESH_TOKEN,

    // Business Error
    @SerializedName("POST_002") FILE_UPLOAD_ERROR,
    @SerializedName("POST_004") NO_POST_EXIST,
}