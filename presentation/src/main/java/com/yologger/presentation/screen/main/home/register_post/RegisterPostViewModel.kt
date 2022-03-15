package com.yologger.presentation.screen.main.home.register_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.yologger.domain.usecase.post.register_post.RegisterPostResult
import com.yologger.domain.usecase.post.register_post.RegisterPostResultError
import com.yologger.domain.usecase.post.register_post.RegisterPostUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.screen.main.more.MoreViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class RegisterPostViewModel @Inject constructor(
      private val registerPostUseCase: RegisterPostUseCase
) : BaseViewModel() {

    sealed class Event {
        object SUCCESS: Event()
        data class FAILURE(val error: Error): Event()
    }

    enum class Error {
        CLIENT_ERROR,
        NETWORK_ERROR,
        FILE_UPLOAD_ERROR,
        NO_SESSION
    }

    private val _liveEvent = SingleLiveEvent<Event>()
    val liveEvent = _liveEvent

    private val _liveIsLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    val liveIsLoading: LiveData<Boolean> = _liveIsLoading

    private val imageUris: ArrayList<Uri> = arrayListOf()
    private val _liveImageUris: MutableLiveData<List<Uri>> = MutableLiveData<List<Uri>>().apply { value = imageUris }
    val liveImageUris: LiveData<List<Uri>> = _liveImageUris

    val liveContent: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }

    fun addImageUris(uris: List<Uri>) {
        imageUris.addAll(uris)
        _liveImageUris.value = imageUris
    }

    fun post() {
        val params = RegisterPostUseCase.Params(content = liveContent.value!!, imageUris = _liveImageUris.value!!)
        registerPostUseCase.execute(params)
            .subscribeBy {
                when(it) {
                    is RegisterPostResult.SUCCESS -> _liveEvent.value = Event.SUCCESS
                    is RegisterPostResult.FAILURE -> {
                        when (it.error) {
                            RegisterPostResultError.CLIENT_ERROR -> _liveEvent.value = Event.FAILURE(Error.CLIENT_ERROR)
                            RegisterPostResultError.INVALID_PARAMS -> _liveEvent.value = Event.FAILURE(Error.CLIENT_ERROR)
                            RegisterPostResultError.FILE_UPLOAD_ERROR -> _liveEvent.value = Event.FAILURE(Error.FILE_UPLOAD_ERROR)
                            RegisterPostResultError.NO_SESSION -> _liveEvent.value = Event.FAILURE(Error.NO_SESSION)
                        }
                    }
                }
            }.addTo(disposables)
    }
}