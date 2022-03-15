package com.yologger.domain.usecase.auth.verify_access_token

sealed class ReissueTokenResponse {
    object SUCCESS: ReissueTokenResponse()
    data class FAILURE(val error: ReissueTokenResponseError): ReissueTokenResponse()
}