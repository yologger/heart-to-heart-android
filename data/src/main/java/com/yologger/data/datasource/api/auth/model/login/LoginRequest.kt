package com.yologger.data.datasource.api.auth.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest
constructor(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)