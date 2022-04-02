package com.yologger.presentation.screen.main.more.settings

import com.yologger.domain.usecase.auth.logout.LogoutResult
import com.yologger.domain.usecase.auth.logout.LogoutResultError
import com.yologger.domain.usecase.auth.logout.LogoutUseCase
import com.yologger.domain.usecase.member.deleteAccount.DeleteAccountResult
import com.yologger.domain.usecase.member.deleteAccount.DeleteAccountResultError
import com.yologger.domain.usecase.member.deleteAccount.DeleteAccountUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel() {

    sealed class State {
        object LOGOUT_SUCCESS : State()
        data class LOGOUT_FAILURE(val error: LogoutError) : State()
        object DELETE_ACCOUNT_SUCCESS: State()
        data class DELETE_ACCOUNT_FAILURE(val error: DeleteAccountError): State()
    }

    enum class LogoutError {
        CLIENT_ERROR,
        NETWORK_ERROR,
    }

    enum class DeleteAccountError {
        NETWORK_ERROR,
        CLIENT_ERROR,
        INVALID_PARAMS,
        NO_SESSION,
        JSON_PARSE_ERROR,
        INVALID_MEMBER_ID,
        AWS_S3_ERROR
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    fun logout() {
        logoutUseCase.execute()
            .subscribeBy {
                when(it) {
                    is LogoutResult.SUCCESS -> _liveState.value = State.LOGOUT_SUCCESS
                    is LogoutResult.FAILURE -> {
                        when(it.error) {
                            LogoutResultError.NETWORK_ERROR -> _liveState.value = State.LOGOUT_FAILURE(LogoutError.NETWORK_ERROR)
                            LogoutResultError.CLIENT_ERROR -> _liveState.value = State.LOGOUT_FAILURE(LogoutError.CLIENT_ERROR)
                            else -> _liveState.value = State.LOGOUT_SUCCESS
                        }
                    }
                }
            }.addTo(disposables)
    }

    fun deleteUser() {
        deleteAccountUseCase.execute()
            .subscribeBy {
                when(it) {
                    is DeleteAccountResult.Success -> _liveState.value = State.DELETE_ACCOUNT_SUCCESS
                    is DeleteAccountResult.Failure -> {
                        when(it.error) {
                            DeleteAccountResultError.AWS_S3_ERROR -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.AWS_S3_ERROR)
                            DeleteAccountResultError.NETWORK_ERROR -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.NETWORK_ERROR)
                            DeleteAccountResultError.JSON_PARSE_ERROR -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.JSON_PARSE_ERROR)
                            DeleteAccountResultError.NO_SESSION -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.NO_SESSION)
                            DeleteAccountResultError.CLIENT_ERROR -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.CLIENT_ERROR)
                            DeleteAccountResultError.INVALID_MEMBER_ID -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.INVALID_MEMBER_ID)
                            DeleteAccountResultError.INVALID_PARAMS -> _liveState.value = State.DELETE_ACCOUNT_FAILURE(DeleteAccountError.INVALID_PARAMS)
                        }
                    }
                }
            }.addTo(disposables)
    }
}