package com.yologger.domain.usecase.member.reportMember

sealed class ReportMemberResult {
    object Success: ReportMemberResult()
    data class Failure(val error: ReportMemberResultError): ReportMemberResult()
}