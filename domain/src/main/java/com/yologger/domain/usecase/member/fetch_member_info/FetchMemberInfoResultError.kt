package com.yologger.domain.usecase.member.fetch_member_info

enum class FetchMemberInfoResultError {
    // Common Error
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    JSON_PARSE_ERROR,

    // Business Error
    INVALID_MEMBER_ID,
}