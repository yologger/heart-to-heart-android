package com.yologger.data.datasource.api.auth.model.logout

import com.google.gson.annotations.SerializedName

data class LogoutResponse constructor(
    @SerializedName("message") val message: String,
)