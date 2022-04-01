package com.yologger.presentation.screen.main.home.userDetail.reportUser

import com.yologger.domain.usecase.member.reportMember.ReportMemberResult
import com.yologger.domain.usecase.member.reportMember.ReportMemberResultError
import com.yologger.domain.usecase.member.reportMember.ReportMemberUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class ReportUserViewModel @Inject constructor(
    private val reportMemberUseCase: ReportMemberUseCase
): BaseViewModel() {

    sealed class State {
        object Success: State()
        data class Failure(val error: Error): State()
    }

    enum class Error {
        CLIENT_ERROR,
        NETWORK_ERROR,
        INVALID_PARAMS,
        JSON_PARSE_ERROR,
        NO_SESSION
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    fun reportMember(memberId: Long) {
        val params = ReportMemberUseCase.Params(targetId = memberId)
        reportMemberUseCase.execute(params)
            .subscribeBy {
                when (it) {
                    is ReportMemberResult.Success -> _liveState.value = State.Success
                    is ReportMemberResult.Failure -> {
                        when(it.error) {
                            ReportMemberResultError.INVALID_PARAMS -> State.Failure(Error.INVALID_PARAMS)
                            ReportMemberResultError.CLIENT_ERROR -> State.Failure(Error.CLIENT_ERROR)
                            ReportMemberResultError.JSON_PARSE_ERROR -> State.Failure(Error.JSON_PARSE_ERROR)
                            ReportMemberResultError.NETWORK_ERROR -> State.Failure(Error.NETWORK_ERROR)
                            ReportMemberResultError.NO_SESSION -> State.Failure(Error.NO_SESSION)
                        }
                    }
                }
            }
    }
}