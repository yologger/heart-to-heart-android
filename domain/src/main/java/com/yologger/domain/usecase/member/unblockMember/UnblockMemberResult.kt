package com.yologger.domain.usecase.member.unblockMember

sealed class UnblockMemberResult {
    object Success: UnblockMemberResult()
    data class Failure(val error: UnblockMemberResultError): UnblockMemberResult()
}