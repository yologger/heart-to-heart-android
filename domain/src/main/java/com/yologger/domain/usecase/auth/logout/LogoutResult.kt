package com.yologger.domain.usecase.auth.logout

sealed class LogoutResult {
    object SUCCESS: LogoutResult()
    data class FAILURE(val error: LogoutResultError): LogoutResult()
}