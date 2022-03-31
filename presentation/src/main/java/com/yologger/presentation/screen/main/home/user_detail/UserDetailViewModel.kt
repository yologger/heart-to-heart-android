package com.yologger.presentation.screen.main.home.user_detail

import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResult
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResultError
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val fetchMemberInfoUseCase: FetchMemberInfoUseCase,
): BaseViewModel() {

    init {
        fetchMemberInfo()
    }

    sealed class State {
        data class FetchMemberInfoSuccess(
            val email: String,
            val nickname: String,
            val name: String,
            val avatarUrl: String?,
            val postSize: Int,
            val followerSize: Int,
            val followingSize: Int): State()
        data class FetchMemberInfoFailure(val error: FetchMemberInfoError): State()
    }

    enum class FetchMemberInfoError {
        CLIENT_ERROR,
        NETWORK_ERROR,
        INVALID_PARAMS,
        JSON_PARSE_ERROR,
        NO_SESSION
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState
    private fun fetchMemberInfo() {
        fetchMemberInfoUseCase.execute()
            .subscribeBy {
                when (it) {
                    is FetchMemberInfoResult.Success -> _liveState.value = State.FetchMemberInfoSuccess(
                        email = it.email,
                        nickname = it.nickname,
                        name = it.name,
                        avatarUrl = it.avatarUrl,
                        postSize = it.postSize,
                        followerSize = it.followerSize,
                        followingSize = it.followingSize
                    )
                    is FetchMemberInfoResult.Failure -> {
                        when(it.error) {
                            FetchMemberInfoResultError.CLIENT_ERROR -> _liveState.value = State.FetchMemberInfoFailure(FetchMemberInfoError.CLIENT_ERROR)
                            FetchMemberInfoResultError.NETWORK_ERROR -> _liveState.value = State.FetchMemberInfoFailure(FetchMemberInfoError.NETWORK_ERROR)
                            FetchMemberInfoResultError.JSON_PARSE_ERROR -> _liveState.value = State.FetchMemberInfoFailure(FetchMemberInfoError.JSON_PARSE_ERROR)
                            FetchMemberInfoResultError.NO_SESSION, FetchMemberInfoResultError.INVALID_MEMBER_ID -> _liveState.value = State.FetchMemberInfoFailure(FetchMemberInfoError.NO_SESSION)
                            FetchMemberInfoResultError.INVALID_PARAMS -> State.FetchMemberInfoFailure(FetchMemberInfoError.CLIENT_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
    }
}