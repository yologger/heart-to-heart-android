package com.yologger.domain.usecase.member.getBlockingMembers

sealed class GetBlockingMembersResult {
    data class Success(val data: GetBlockingMembersResultData): GetBlockingMembersResult()
    data class Failure(val error: GetBlockingMembersResultError): GetBlockingMembersResult()
}