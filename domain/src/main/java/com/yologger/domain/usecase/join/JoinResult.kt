package com.yologger.domain.usecase.join

sealed class JoinResult {
    object SUCCESS: JoinResult()
    data class FAILURE(val error: JoinError): JoinResult()
}