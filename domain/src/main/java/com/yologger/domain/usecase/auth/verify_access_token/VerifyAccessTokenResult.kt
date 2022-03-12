package com.yologger.domain.usecase.auth.verify_access_token

sealed class VerifyAccessTokenResult {
    data class SUCCESS(val data: VerifyAccessTokenResultData): VerifyAccessTokenResult()
    data class FAILURE(val error: VerifyAccessTokenResultError): VerifyAccessTokenResult()
}