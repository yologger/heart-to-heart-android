package com.yologger.data.datasource.api.post.model.register_post

import com.google.gson.annotations.SerializedName

data class RegisterPostFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: RegisterPostFailureCode,
    @SerializedName("status") val status: Int
)