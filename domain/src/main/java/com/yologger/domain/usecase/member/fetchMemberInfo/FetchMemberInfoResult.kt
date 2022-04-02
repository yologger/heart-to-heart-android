package com.yologger.domain.usecase.member.fetchMemberInfo

sealed class FetchMemberInfoResult {
    data class Success(
        val id: Long,
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
