package com.yologger.data.datasource.api.auth.model.verifyAccessToken

import com.google.gson.annotations.SerializedName

data class VerifyAccessTokenSuccessResponse constructor(
    @SerializedName("message") val message: String,
)