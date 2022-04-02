package com.yologger.data.datasource.api.member.model.deleteAccount

import com.google.gson.annotations.SerializedName

data class DeleteAccountFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: DeleteAccountFailureResponseCode,
    @SerializedName("status") val status: Int
)