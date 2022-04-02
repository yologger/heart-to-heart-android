package com.yologger.presentation.screen.main.more.settings.handleBlockedUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersResult
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersResultError
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersUseCase
import com.yologger.domain.usecase.member.getBlockingMembers.MemberData
import com.yologger.domain.usecase.member.unblockMember.UnblockMemberResult
import com.yologger.domain.usecase.member.unblockMember.UnblockMemberResultError
import com.yologger.domain.usecase.member.unblockMember.UnblockMemberUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class HandleBlockedUsersViewModel @Inject constructor(
    private val getBlockingMembersUseCase: GetBlockingMembersUseCase,
    private val unblockMemberUseCase: UnblockMemberUseCase
) : BaseViewModel() {

    sealed class State {
        object GetBlockingMembersSuccess: State()
        data class GetBlockingMembersFailure(val error: GetBlockingMembersError): State()
        data class UnblockMemberSuccess(val memberId: Long): State()
        data class UnblockMemberFailure(val error: UnblockMemberError): State()
    }

    enum class GetBlockingMembersError {
        NETWORK_ERROR,
        CLIENT_ERROR,
        JSON_PARSE_ERROR,
        NO_SESSION,
        INVALID_MEMBER_ID
    }

    enum class UnblockMemberError {
        CLIENT_ERROR,
        NETWORK_ERROR,
        INVALID_PARAMS,
        JSON_PARSE_ERROR,
        NO_SESSION,
        INVALID_MEMBER_ID,
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private val _liveIsLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    private var members = mutableListOf<MemberData>()
    private var _liveMembers: MutableLiveData<MutableList<MemberData>> = MutableLiveData(members)
    val liveMembers = _liveMembers

    init {
        fetchBlockingMembers()
    }

    private fun fetchBlockingMembers() {
        _liveIsLoading.value = true

        getBlockingMembersUseCase.execute()
            .take(1)
            .subscribeBy {
                _liveIsLoading.value = false
                when (it) {
                    is GetBlockingMembersResult.Success -> {
                        members.addAll(it.data.members)
                        _liveMembers.value = members
                    }
                    is GetBlockingMembersResult.Failure -> {
                        when(it.error) {
                            GetBlockingMembersResultError.INVALID_MEMBER_ID -> _liveState.value = State.GetBlockingMembersFailure(GetBlockingMembersError.NO_SESSION)
                            GetBlockingMembersResultError.NETWORK_ERROR -> _liveState.value = State.GetBlockingMembersFailure(GetBlockingMembersError.NETWORK_ERROR)
                            GetBlockingMembersResultError.CLIENT_ERROR, GetBlockingMembersResultError.INVALID_PARAMS -> _liveState.value = State.GetBlockingMembersFailure(GetBlockingMembersError.CLIENT_ERROR)
                            GetBlockingMembersResultError.JSON_PARSE_ERROR -> _liveState.value = State.GetBlockingMembersFailure(GetBlockingMembersError.JSON_PARSE_ERROR)
                            GetBlockingMembersResultError.NO_SESSION -> _liveState.value = State.GetBlockingMembersFailure(GetBlockingMembersError.NO_SESSION)
                        }
                    }
                }
            }.addTo(disposables)
    }

    fun unblock(memberId: Long) {
        val params = UnblockMemberUseCase.Params(memberId = memberId)
        unblockMemberUseCase.execute(params)
            .subscribeBy {
                when(it) {
                    is UnblockMemberResult.Success -> {
                        for ((index, member) in members.withIndex()) {
                            if (member.id == memberId) {
                                members.removeAt(index)
                                _liveMembers.value = members
                                break
                            }
                        }
                        _liveState.value = State.UnblockMemberSuccess(memberId = memberId)
                    }
                    is UnblockMemberResult.Failure -> {
                        when(it.error) {
                            UnblockMemberResultError.NETWORK_ERROR -> _liveState.value = State.UnblockMemberFailure(UnblockMemberError.NETWORK_ERROR)
                            UnblockMemberResultError.JSON_PARSE_ERROR -> _liveState.value = State.UnblockMemberFailure(UnblockMemberError.JSON_PARSE_ERROR)
                            UnblockMemberResultError.CLIENT_ERROR -> _liveState.value = State.UnblockMemberFailure(UnblockMemberError.CLIENT_ERROR)
                            UnblockMemberResultError.NO_SESSION -> _liveState.value = State.UnblockMemberFailure(UnblockMemberError.NO_SESSION)
                            UnblockMemberResultError.INVALID_PARAMS -> _liveState.value = State.UnblockMemberFailure(UnblockMemberError.INVALID_PARAMS)
                            UnblockMemberResultError.INVALID_MEMBER_ID -> State.UnblockMemberFailure(UnblockMemberError.INVALID_MEMBER_ID)
                        }
                    }
                }
            }.addTo(disposables)
    }
}