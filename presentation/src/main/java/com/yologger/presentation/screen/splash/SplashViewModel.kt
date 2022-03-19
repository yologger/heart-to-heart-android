package com.yologger.presentation.screen.splash

import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenResult
import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenResultData
import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenResultError
import com.yologger.domain.usecase.auth.verify_access_token.VerifyAccessTokenUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val verifyAccessTokenUseCase: VerifyAccessTokenUseCase
) : BaseViewModel() {

    init {
        verifyAccessToken()
    }

    sealed class State {
        data class Success(val result: Result): State()
        data class Failure(val error: Error): State()
    }

    enum class Result {
        LOGGED_IN,
        NOT_LOGGED_IN
    }

    enum class Error {
        NETWORK_ERROR,
        CLIENT_ERROR,
        JSON_PARSE_ERROR
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private fun verifyAccessToken() {
        verifyAccessTokenUseCase.execute()
            .subscribeBy { result ->
                when(result) {
                    is VerifyAccessTokenResult.SUCCESS -> {
                        when(result.data) {
                            VerifyAccessTokenResultData.LOGGED_IN -> _liveState.value = State.Success(Result.LOGGED_IN)
                            VerifyAccessTokenResultData.NOT_LOGGED_IN -> _liveState.value = State.Success(Result.NOT_LOGGED_IN)
                        }
                    }
                    is VerifyAccessTokenResult.FAILURE -> {
                        when(result.error) {
                            VerifyAccessTokenResultError.NETWORK_ERROR -> _liveState.value = State.Failure(Error.NETWORK_ERROR)
                            VerifyAccessTokenResultError.CLIENT_ERROR -> _liveState.value = State.Failure(Error.CLIENT_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
    }
}