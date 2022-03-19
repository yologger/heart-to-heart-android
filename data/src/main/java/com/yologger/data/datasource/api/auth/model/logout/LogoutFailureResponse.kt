package com.yologger.data.datasource.api.auth.model.logout

import com.google.gson.annotations.SerializedName

data class LogoutFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: LogoutFailureResponseCode,
    @SerializedName("status") val status: Int
)