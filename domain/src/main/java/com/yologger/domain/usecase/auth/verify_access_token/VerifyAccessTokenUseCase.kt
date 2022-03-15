package com.yologger.domain.usecase.auth.verify_access_token

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class VerifyAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : ObservableUseCase<VerifyAccessTokenUseCase.Params, VerifyAccessTokenResult>() {

    data class Params(val data: String)

    override fun call(params: Params?): Observable<VerifyAccessTokenResult> {
        return Observable.create { emitter ->
            // Verify Access Token
            val verifyAccessTokenResponse = authRepository.verifyAccessToken()
            when (verifyAccessTokenResponse) {
                is VerifyAccessTokenResponse.SUCCESS -> emitter.onNext(VerifyAccessTokenResult.SUCCESS(VerifyAccessTokenResultData.LOGGED_IN))
                is VerifyAccessTokenResponse.FAILURE -> when(verifyAccessTokenResponse.error) {
                    VerifyAccessTokenResponseError.NETWORK_ERROR -> emitter.onNext(VerifyAccessTokenResult.FAILURE(VerifyAccessTokenResultError.NETWORK_ERROR))
                    VerifyAccessTokenResponseError.CLIENT_ERROR -> emitter.onNext(VerifyAccessTokenResult.FAILURE(VerifyAccessTokenResultError.CLIENT_ERROR))
                    VerifyAccessTokenResponseError.ACCESS_TOKEN_EMPTY -> emitter.onNext(VerifyAccessTokenResult.SUCCESS(VerifyAccessTokenResultData.NOT_LOGGED_IN))
                    VerifyAccessTokenResponseError.INVALID_ACCESS_TOKEN -> emitter.onNext(VerifyAccessTokenResult.SUCCESS(VerifyAccessTokenResultData.NOT_LOGGED_IN))
                    VerifyAccessTokenResponseError.EXPIRED_ACCESS_TOKEN -> {
                        // Refresh token
                        val reissueTokenResponse = authRepository.reissueToken()
                        when(reissueTokenResponse) {
                            is ReissueTokenResponse.SUCCESS -> {
                                emitter.onNext(VerifyAccessTokenResult.SUCCESS(VerifyAccessTokenResultData.LOGGED_IN))
                            }
                            is ReissueTokenResponse.FAILURE -> when(reissueTokenResponse.error) {
                                ReissueTokenResponseError.CLIENT_ERROR -> emitter.onNext(VerifyAccessTokenResult.FAILURE(VerifyAccessTokenResultError.CLIENT_ERROR))
                                ReissueTokenResponseError.NETWORK_ERROR -> emitter.onNext(VerifyAccessTokenResult.FAILURE(VerifyAccessTokenResultError.NETWORK_ERROR))
                                else -> {
                                    emitter.onNext(VerifyAccessTokenResult.SUCCESS(VerifyAccessTokenResultData.NOT_LOGGED_IN))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}