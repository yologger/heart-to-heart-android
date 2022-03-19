package com.yologger.domain.usecase.member.fetch_member_info

sealed class FetchMemberInfoResult {
    data class Success(val email: String, val nickname: String, val avatarUrl: String?): FetchMemberInfoResult()
    object Failure: FetchMemberInfoResult()
}
