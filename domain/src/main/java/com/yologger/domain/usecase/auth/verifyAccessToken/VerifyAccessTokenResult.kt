package com.yologger.domain.usecase.auth.verifyAccessToken

sealed class VerifyAccessTokenResult {
    data class SUCCESS(val data: VerifyAccessTokenResultData): VerifyAccessTokenResult()
    data class FAILURE(val error: VerifyAccessTokenResultError): VerifyAccessTokenResult()
}