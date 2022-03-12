package com.yologger.domain.usecase.auth.join

sealed class JoinResult {
    object SUCCESS: JoinResult()
    data class FAILURE(val error: JoinResultError): JoinResult()
}