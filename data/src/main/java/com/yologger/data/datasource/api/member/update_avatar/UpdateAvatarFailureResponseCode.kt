package com.yologger.data.datasource.api.member.update_avatar

import com.google.gson.annotations.SerializedName

enum class UpdateAvatarFailureResponseCode {
    // Common Error
    @SerializedName("GLOBAL_001") NOT_FOUND,
    @SerializedName("GLOBAL_002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL_006") ILLEGAL_STATE,
    @SerializedName("LOCAL_001") NO_ACCESS_TOKEN_IN_LOCAL,
    @SerializedName("AUTH_007") INVALID_REFRESH_TOKEN,
    @SerializedName("AUTH_008") EXPIRED_REFRESH_TOKEN,

    // Business Error
    @SerializedName("MEMBER001") MEMBER_NOT_EXIST,
    @SerializedName("MEMBER002") FILE_UPLOAD_ERROR,
    @SerializedName("MEMBER003") IO_ERROR,
    @SerializedName("MEMBER004") INVALID_CONTENT_TYPE,
}