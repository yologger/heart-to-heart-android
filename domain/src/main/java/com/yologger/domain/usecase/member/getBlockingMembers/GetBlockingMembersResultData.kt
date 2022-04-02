package com.yologger.domain.usecase.member.getBlockingMembers

data class GetBlockingMembersResultData (
    val size: Int,
    val members: List<MemberData>
)
