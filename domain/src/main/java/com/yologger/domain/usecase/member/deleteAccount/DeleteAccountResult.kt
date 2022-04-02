package com.yologger.domain.usecase.member.deleteAccount

sealed class DeleteAccountResult {
    object Success: DeleteAccountResult()
    data class Failure(val error: DeleteAccountResultError): DeleteAccountResult()
}