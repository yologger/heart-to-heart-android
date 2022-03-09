package com.yologger.domain.usecase.login

sealed class LoginResult {
    object SUCCESS: LoginResult()
    data class FAILURE(val error: LoginError): LoginResult()
}