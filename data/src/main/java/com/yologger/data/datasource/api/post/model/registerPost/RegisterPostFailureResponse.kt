package com.yologger.data.datasource.api.post.model.registerPost

import com.google.gson.annotations.SerializedName

data class RegisterPostFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: RegisterPostFailureResponseCode,
    @SerializedName("status") val status: Int
)