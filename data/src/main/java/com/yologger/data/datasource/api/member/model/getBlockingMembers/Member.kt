package com.yologger.data.datasource.api.member.model.getBlockingMembers

import com.google.gson.annotations.SerializedName
import com.yologger.domain.usecase.member.getBlockingMembers.MemberData

data class Member (
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String,
) {
    fun toData(): MemberData {
        return MemberData(
            id = this.id,
            email = this.email,
            nickname = this.nickname,
            name = this.name,
            avatarUrl = this.avatarUrl
        )
    }
}