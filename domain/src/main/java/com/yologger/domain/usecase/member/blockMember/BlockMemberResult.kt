package com.yologger.domain.usecase.member.blockMember

sealed class BlockMemberResult {
    object Success: BlockMemberResult()
    data class Failure(val error: BlockMemberResultError): BlockMemberResult()
}