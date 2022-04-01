package com.yologger.domain.usecase.member.getBlockingMembers

data class MemberData(
    val id: Long,
    val email: String,
    val nickname: String,
    val name: String,
    val avatarUrl: String?,
)