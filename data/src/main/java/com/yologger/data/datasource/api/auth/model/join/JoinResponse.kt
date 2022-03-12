package com.yologger.data.datasource.api.auth.model.join

import com.google.gson.annotations.SerializedName

data class JoinResponse constructor(
    @SerializedName("user_id") val userId: String
)