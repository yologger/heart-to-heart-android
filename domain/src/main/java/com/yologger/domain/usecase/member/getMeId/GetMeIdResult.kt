package com.yologger.domain.usecase.member.getMeId

sealed class GetMeIdResult {
    data class Success(val memberId: Long): GetMeIdResult()
    data class Failure(val error: GetMeIdResultError): GetMeIdResult()
}