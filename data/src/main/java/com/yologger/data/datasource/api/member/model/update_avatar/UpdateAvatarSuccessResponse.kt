package com.yologger.data.datasource.api.member.model.update_avatar

import com.google.gson.annotations.SerializedName

data class UpdateAvatarSuccessResponse constructor(
    @SerializedName("member_id") val memberId: Long,
    @SerializedName("avatar_url") val avatarUrl: String
)
