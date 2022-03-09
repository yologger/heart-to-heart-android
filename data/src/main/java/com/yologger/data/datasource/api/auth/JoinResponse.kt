package com.yologger.data.datasource.api.auth

import com.google.gson.annotations.SerializedName

data class JoinResponse constructor(
    @SerializedName("user_id") val userId: String
)