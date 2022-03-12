package com.yologger.data.datasource.api.auth.model.login

import com.google.gson.annotations.SerializedName

data class LoginFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: LoginFailureCode,
    @SerializedName("status") val status: Int
)