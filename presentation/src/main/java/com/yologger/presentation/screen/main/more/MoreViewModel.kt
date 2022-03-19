package com.yologger.presentation.screen.main.more

import android.net.Uri
import com.yologger.domain.usecase.member.update_avatar.UpdateAvatarResult
import com.yologger.domain.usecase.member.update_avatar.UpdateAvatarResultError
import com.yologger.domain.usecase.member.update_avatar.UpdateAvatarUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val updateAvatarUseCase: UpdateAvatarUseCase
): BaseViewModel() {

    sealed class State {
        data class SUCCESS(val imageUrl: String): State()
        data class FAILURE(val error: Error): State()
    }

    enum class Error {
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

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState


    fun updateAvatar(uri: Uri) {
        val params = UpdateAvatarUseCase.Params(imageUri = uri)
        updateAvatarUseCase.execute(params)
            .subscribeBy {
                when(it) {
                    is UpdateAvatarResult.Success -> _liveState.value = State.SUCCESS(imageUrl = it.avatarUrl)
                    is UpdateAvatarResult.Failure -> {
                        when (it.error) {
                            UpdateAvatarResultError.NETWORK_ERROR -> _liveState.value = State.FAILURE(Error.NETWORK_ERROR)
                            UpdateAvatarResultError.CLIENT_ERROR -> _liveState.value = State.FAILURE(Error.CLIENT_ERROR)
                            UpdateAvatarResultError.INVALID_PARAMS -> _liveState.value = State.FAILURE(Error.INVALID_PARAMS)
                            UpdateAvatarResultError.JSON_PARSE_ERROR -> _liveState.value = State.FAILURE(Error.JSON_PARSE_ERROR)
                            UpdateAvatarResultError.NO_SESSION -> _liveState.value = State.FAILURE(Error.NO_SESSION)
                            UpdateAvatarResultError.FILE_UPLOAD_ERROR -> _liveState.value = State.FAILURE(Error.FILE_UPLOAD_ERROR)
                            UpdateAvatarResultError.IO_ERROR -> _liveState.value = State.FAILURE(Error.IO_ERROR)
                            UpdateAvatarResultError.INVALID_CONTENT_TYPE -> _liveState.value = State.FAILURE(Error.INVALID_CONTENT_TYPE)
                            UpdateAvatarResultError.MEMBER_NOT_EXIST -> _liveState.value = State.FAILURE(Error.MEMBER_NOT_EXIST)
                        }
                    }
                }
            }.addTo(disposables)
    }
}