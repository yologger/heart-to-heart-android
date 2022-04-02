package com.yologger.presentation.screen.main.more

import android.net.Uri
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResult
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResultError
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoUseCase
import com.yologger.domain.usecase.member.updateAvatar.UpdateAvatarResult
import com.yologger.domain.usecase.member.updateAvatar.UpdateAvatarResultError
import com.yologger.domain.usecase.member.updateAvatar.UpdateAvatarUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val fetchMemberInfoUseCase: FetchMemberInfoUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase
): BaseViewModel() {

    init {
        fetchMemberInfo()
    }

    sealed class State {
        data class UpdateAvatarSuccess(val imageUrl: String): State()
        data class UpdateAvatarFailure(val error: UpdateAvatarError): State()
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

    enum class UpdateAvatarError {
        CLIENT_ERROR,
        NETWORK_ERROR,
        INVALID_PARAMS,
        JSON_PARSE_ERROR,
        NO_SESSION,
        FILE_UPLOAD_ERROR,
        IO_ERROR,
        INVALID_CONTENT_TYPE,
        MEMBER_NOT_EXIST
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

    fun updateAvatar(uri: Uri) {
        val params = UpdateAvatarUseCase.Params(imageUri = uri)
        updateAvatarUseCase.execute(params)
            .subscribeBy {
                when(it) {
                    is UpdateAvatarResult.Success -> _liveState.value = State.UpdateAvatarSuccess(imageUrl = it.avatarUrl)
                    is UpdateAvatarResult.Failure -> {
                        when (it.error) {
                            UpdateAvatarResultError.NETWORK_ERROR -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.NETWORK_ERROR)
                            UpdateAvatarResultError.CLIENT_ERROR -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.CLIENT_ERROR)
                            UpdateAvatarResultError.INVALID_PARAMS -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.INVALID_PARAMS)
                            UpdateAvatarResultError.JSON_PARSE_ERROR -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.JSON_PARSE_ERROR)
                            UpdateAvatarResultError.NO_SESSION -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.NO_SESSION)
                            UpdateAvatarResultError.FILE_UPLOAD_ERROR -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.FILE_UPLOAD_ERROR)
                            UpdateAvatarResultError.IO_ERROR -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.IO_ERROR)
                            UpdateAvatarResultError.INVALID_CONTENT_TYPE -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.INVALID_CONTENT_TYPE)
                            UpdateAvatarResultError.MEMBER_NOT_EXIST -> _liveState.value = State.UpdateAvatarFailure(UpdateAvatarError.MEMBER_NOT_EXIST)
                        }
                    }
                }
            }.addTo(disposables)
    }
}