package com.yologger.domain.usecase.auth.verifyAccessToken

sealed class VerifyAccessTokenResponse {
    object SUCCESS: VerifyAccessTokenResponse()
    data class FAILURE(val error: VerifyAccessTokenResponseError): VerifyAccessTokenResponse()
}