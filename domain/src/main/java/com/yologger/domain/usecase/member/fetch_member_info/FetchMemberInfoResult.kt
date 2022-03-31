package com.yologger.domain.usecase.member.fetch_member_info

sealed class FetchMemberInfoResult {
    data class Success(
        val email: String,
        val nickname: String,
        val name: String,
        val avatarUrl: String?,
        val postSize: Int,
        val followerSize: Int,
        val followingSize: Int
    ): FetchMemberInfoResult()
    data class Failure(val error: FetchMemberInfoResultError): FetchMemberInfoResult()
}
