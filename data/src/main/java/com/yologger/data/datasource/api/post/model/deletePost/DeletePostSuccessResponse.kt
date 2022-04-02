package com.yologger.data.datasource.api.post.model.deletePost

import com.google.gson.annotations.SerializedName

data class DeletePostSuccessResponse(
    @SerializedName("message") val message: String
)