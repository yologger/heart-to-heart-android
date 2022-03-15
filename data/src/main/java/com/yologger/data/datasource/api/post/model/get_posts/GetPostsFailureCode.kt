package com.yologger.data.datasource.api.post.model.get_posts

import com.google.gson.annotations.SerializedName

enum class GetPostsFailureCode {
    @SerializedName("GLOBAL001") NOT_FOUND,
    @SerializedName("GLOBAL002") INVALID_INPUT_VALUE,
    @SerializedName("GLOBAL006") ILLEGAL_STATE,

    @SerializedName("GLOBAL100") MISSING_AUTHORIZATION_HEADER,
    @SerializedName("GLOBAL101") BEARER_NOT_INCLUDED,
}