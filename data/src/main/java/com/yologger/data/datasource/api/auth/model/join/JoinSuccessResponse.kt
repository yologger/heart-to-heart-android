package com.yologger.data.datasource.api.auth.model.join

import com.google.gson.annotations.SerializedName

data class JoinSuccessResponse constructor(
    @SerializedName("member_id") val memberId: String
)