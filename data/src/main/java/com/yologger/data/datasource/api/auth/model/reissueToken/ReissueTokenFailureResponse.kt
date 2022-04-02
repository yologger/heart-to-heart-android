package com.yologger.data.datasource.api.auth.model.reissueToken

import com.google.gson.annotations.SerializedName

data class ReissueTokenFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: ReissueTokenFailureResponseCode,
    @SerializedName("status") val status: Int
)
