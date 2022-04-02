package com.yologger.domain.usecase.auth.verifyAccessToken

sealed class ReissueTokenResponse {
    object SUCCESS: ReissueTokenResponse()
    data class FAILURE(val error: ReissueTokenResponseError): ReissueTokenResponse()
}