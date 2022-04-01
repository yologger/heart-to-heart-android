package com.yologger.presentation.screen.main.home.registerPost

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.post.getPosts.PostData
import com.yologger.domain.usecase.post.registerPost.RegisterPostResult
import com.yologger.domain.usecase.post.registerPost.RegisterPostResultError
import com.yologger.domain.usecase.post.registerPost.RegisterPostUseCase
import com.yologger.presentation.base.BaseViewModel
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
        data class Success(val post: PostData): Event()
        data class Failure(val error: Error): Event()
    }

    enum class Error {
        CLIENT_ERROR,
        NETWORK_ERROR,
        FILE_UPLOAD_ERROR,
        NO_SESSION,
        FILE_SIZE_LIMIT_EXCEEDED,
        JSON_PARSE_ERROR,
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

    fun getCurrentImagesCount() = imageUris.size

    fun post() {
        _liveIsLoading.value = true
        val params = RegisterPostUseCase.Params(content = liveContent.value!!, imageUris = _liveImageUris.value!!)
        registerPostUseCase.execute(params)
            .subscribeBy {
                _liveIsLoading.value = false
                when(it) {
                    is RegisterPostResult.SUCCESS -> {
                        val createdData = it.data
                        val createdPost = PostData(
                            id = createdData.postId,
                            writerId = createdData.writerId,
                            writerEmail = createdData.writerEmail,
                            writerNickname = createdData.writerNickname,
                            avatarUrl = createdData.avatarUrl,
                            content = createdData.content,
                            imageUrls = createdData.imageUrls,
                            createdAt = createdData.createdAt,
                            updatedAt = createdData.updatedAt
                        )
                        _liveEvent.value = Event.Success(post = createdPost)
                    }
                    is RegisterPostResult.FAILURE -> {
                        when (it.error) {
                            RegisterPostResultError.CLIENT_ERROR -> _liveEvent.value = Event.Failure(Error.CLIENT_ERROR)
                            RegisterPostResultError.INVALID_PARAMS -> _liveEvent.value = Event.Failure(Error.CLIENT_ERROR)
                            RegisterPostResultError.FILE_UPLOAD_ERROR -> _liveEvent.value = Event.Failure(Error.FILE_UPLOAD_ERROR)
                            RegisterPostResultError.NO_SESSION -> _liveEvent.value = Event.Failure(Error.NO_SESSION)
                            RegisterPostResultError.FILE_SIZE_LIMIT_EXCEEDED -> _liveEvent.value = Event.Failure(Error.FILE_SIZE_LIMIT_EXCEEDED)
                            RegisterPostResultError.NETWORK_ERROR -> _liveEvent.value = Event.Failure(Error.NETWORK_ERROR)
                            RegisterPostResultError.JSON_PARSE_ERROR -> _liveEvent.value = Event.Failure(Error.JSON_PARSE_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
    }
}