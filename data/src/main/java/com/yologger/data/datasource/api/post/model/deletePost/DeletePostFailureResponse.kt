package com.yologger.data.datasource.api.post.model.deletePost

import com.google.gson.annotations.SerializedName

data class DeletePostFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: DeletePostFailureResponseCode,
    @SerializedName("status") val status: Int
)
