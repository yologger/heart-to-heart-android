package com.yologger.domain.usecase.member.reportMember

enum class ReportMemberResultError {
    // Common Error
    NETWORK_ERROR,
    CLIENT_ERROR,
    INVALID_PARAMS,
    NO_SESSION,
    JSON_PARSE_ERROR,

    // Business Error
}