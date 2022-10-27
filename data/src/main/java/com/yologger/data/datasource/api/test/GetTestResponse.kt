package com.yologger.data.datasource.api.test

import com.google.gson.annotations.SerializedName

data class GetTestResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)
