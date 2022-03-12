package com.yologger.data.datasource.api.auth.model.verify_access_token

import com.google.gson.annotations.SerializedName

data class VerifyAccessTokenSuccessResponse constructor(
    @SerializedName("message") val message: String,
)