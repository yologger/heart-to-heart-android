package com.yologger.presentation.screen.main.home.userDetail

import com.yologger.domain.usecase.member.blockMember.BlockMemberResult
import com.yologger.domain.usecase.member.blockMember.BlockMemberResultError
import com.yologger.domain.usecase.member.blockMember.BlockMemberUseCase
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResult
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResultError
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val fetchMemberInfoUseCase: FetchMemberInfoUseCase,
    private val blockMemberUseCase: BlockMemberUseCase
): BaseViewModel() {

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
        object BlockMemberSuccess: State()
        data class BlockMemberFailure(val error: BlockMemberError): State()
    }

    enum class FetchMemberInfoError {
        CLIENT_ERROR,
        NETWORK_ERROR,
        INVALID_PARAMS,
        JSON_PARSE_ERROR,
        NO_SESSION
    }

    enum class BlockMemberError {
        CLIENT_ERROR,
        NETWORK_ERROR,
        INVALID_PARAMS,
        JSON_PARSE_ERROR,
        NO_SESSION,
        INVALID_MEMBER_ID,
        ALREADY_BLOCKING
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private var memberId: Long = 0

    fun setMemberId(memberId: Long) {
        this.memberId = memberId
    }

    fun fetchMemberInfo() {
        val params = FetchMemberInfoUseCase.Params(memberId = memberId)
        fetchMemberInfoUseCase.execute(params)
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

    fun blockMember() {
        val params = BlockMemberUseCase.Params(memberId = memberId)
        blockMemberUseCase.execute(params)
            .subscribeBy {
                when(it) {
                    is BlockMemberResult.Success -> _liveState.value = State.BlockMemberSuccess
                    is BlockMemberResult.Failure -> {
                        when(it.error) {
                            BlockMemberResultError.NETWORK_ERROR -> _liveState.value = State.BlockMemberFailure(BlockMemberError.NETWORK_ERROR)
                            BlockMemberResultError.JSON_PARSE_ERROR -> _liveState.value = State.BlockMemberFailure(BlockMemberError.JSON_PARSE_ERROR)
                            BlockMemberResultError.CLIENT_ERROR -> _liveState.value = State.BlockMemberFailure(BlockMemberError.CLIENT_ERROR)
                            BlockMemberResultError.NO_SESSION -> _liveState.value = State.BlockMemberFailure(BlockMemberError.NO_SESSION)
                            BlockMemberResultError.INVALID_PARAMS -> _liveState.value = State.BlockMemberFailure(BlockMemberError.INVALID_PARAMS)
                            BlockMemberResultError.ALREADY_BLOCKING -> _liveState.value = State.BlockMemberFailure(BlockMemberError.ALREADY_BLOCKING)
                            BlockMemberResultError.INVALID_MEMBER_ID -> State.BlockMemberFailure(BlockMemberError.INVALID_MEMBER_ID)
                        }
                    }
                }
            }.addTo(disposables)
    }
}