package com.yologger.presentation.screen.main.more

import com.yologger.domain.usecase.auth.logout.LogoutResult
import com.yologger.domain.usecase.auth.logout.LogoutResultError
import com.yologger.domain.usecase.auth.logout.LogoutUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): BaseViewModel() {

    sealed class State {
        object SUCCESS: State()
        data class FAILURE(val error: Error): State()
    }

    enum class Error {
        CLIENT_ERROR,
        NETWORK_ERROR,
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    fun logout() {
        logoutUseCase.execute()
            .subscribeBy {
                when(it) {
                    is LogoutResult.SUCCESS -> _liveState.value = State.SUCCESS
                    is LogoutResult.FAILURE -> {
                        when(it.error) {
                            LogoutResultError.NETWORK_ERROR -> _liveState.value = State.FAILURE(Error.NETWORK_ERROR)
                            LogoutResultError.CLIENT_ERROR -> _liveState.value = State.FAILURE(Error.CLIENT_ERROR)
                            else -> _liveState.value = State.SUCCESS
                        }
                    }
                }
            }.addTo(disposables)
    }
}