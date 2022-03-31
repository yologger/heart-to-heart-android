package com.yologger.data.datasource.api.member.model.update_avatar

import com.google.gson.annotations.SerializedName

data class UpdateAvatarFailureResponse constructor(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: UpdateAvatarFailureResponseCode,
    @SerializedName("status") val status: Int
)