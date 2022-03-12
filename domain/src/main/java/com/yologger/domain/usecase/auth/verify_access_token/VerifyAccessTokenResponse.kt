package com.yologger.domain.usecase.auth.verify_access_token

sealed class VerifyAccessTokenResponse {
    object SUCCESS: VerifyAccessTokenResponse()
    data class FAILURE(val error: VerifyAccessTokenResponseError): VerifyAccessTokenResponse()
}