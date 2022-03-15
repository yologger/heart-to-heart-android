package com.yologger.domain.usecase.auth.login

sealed class LoginResult {
    object SUCCESS: LoginResult()
    data class FAILURE(val error: LoginResultError): LoginResult()
}