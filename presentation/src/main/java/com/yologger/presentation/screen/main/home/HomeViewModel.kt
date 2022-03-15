package com.yologger.presentation.screen.main.home

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.yologger.domain.usecase.post.get_posts.GetPostsResult
import com.yologger.domain.usecase.post.get_posts.GetPostsUseCase
import com.yologger.domain.usecase.post.get_posts.PostData
import com.yologger.presentation.screen.auth.login.LoginViewModel
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel() {

    sealed class State {
        object Success: State()
        data class Failure(val error: Error): State()
    }

    enum class Error {
        NETWORK_ERROR,
        CLIENT_ERROR
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private val posts = mutableListOf<PostData>()
    private var _livePosts: MutableLiveData<MutableList<PostData>> = MutableLiveData(posts)
    val livePosts = _livePosts

    init {
        fetchData()
    }
    
    private fun fetchData() {
        val params = GetPostsUseCase.Params(size = 30, page = 1)
        getPostsUseCase.execute(params)
            .subscribeBy { 
                when (it) {
                    is GetPostsResult.Success -> {
                        posts.addAll(it.data.posts)
                        _livePosts.value = posts
                    }
                    is GetPostsResult.Failure -> {}
                }
            }.addTo(disposables)
    }
}