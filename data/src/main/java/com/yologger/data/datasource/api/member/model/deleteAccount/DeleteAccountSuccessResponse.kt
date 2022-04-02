package com.yologger.data.datasource.api.member.model.deleteAccount

import com.google.gson.annotations.SerializedName

data class DeleteAccountSuccessResponse constructor(
    @SerializedName("message") val message: String,
)