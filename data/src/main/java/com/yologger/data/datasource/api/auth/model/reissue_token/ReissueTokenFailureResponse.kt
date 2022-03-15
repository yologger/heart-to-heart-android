package com.yologger.data.datasource.api.auth.model.reissue_token

import com.google.gson.annotations.SerializedName
import com.yologger.data.datasource.api.auth.model.verify_access_token.VerifyAccessTokenFailureCode

data class ReissueTokenFailureResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: ReissueTokenFailureResponseCode,
    @SerializedName("status") val status: Int
)
