package com.yologger.domain.usecase.logout

sealed class LogoutResult {
    object SUCCESS: LogoutResult()
    data class FAILURE(val error: LogoutError): LogoutResult()
}