package com.yologger.presentation.screen.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.post.get_posts.GetPostsResult
import com.yologger.domain.usecase.post.get_posts.GetPostsResultError
import com.yologger.domain.usecase.post.get_posts.GetPostsUseCase
import com.yologger.domain.usecase.post.get_posts.PostData
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
        CLIENT_ERROR,
        JSON_PARSE_ERROR,
        NO_POST_EXIST,
        NO_SESSION
    }
    
    private var page = 0
    private val size = 10
    private var hasMore = true
    
    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private val _liveIsLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    private var posts = mutableListOf<PostData>()
    private var _livePosts: MutableLiveData<MutableList<PostData>> = MutableLiveData(posts)
    val livePosts = _livePosts

    init {
        loadData()
    }
    
    fun loadData() {
        if (!hasMore) { return }
        _liveIsLoading.value = true
        
        val params = GetPostsUseCase.Params(size = size, page = page)
        getPostsUseCase.execute(params)
            .take(1)
            .subscribeBy {
                _liveIsLoading.value = false
                when (it) {
                    is GetPostsResult.Success -> {
                        posts.addAll(it.data.posts)
                        hasMore = it.data.posts.size == size
                        _livePosts.value = posts
                        page += 1
                    }
                    is GetPostsResult.Failure -> {
                        when(it.error) {
                            GetPostsResultError.NETWORK_ERROR -> _liveState.value = State.Failure(Error.NETWORK_ERROR)
                            GetPostsResultError.CLIENT_ERROR, GetPostsResultError.INVALID_PARAMS -> _liveState.value = State.Failure(Error.CLIENT_ERROR)
                            GetPostsResultError.JSON_PARSE_ERROR -> _liveState.value = State.Failure(Error.JSON_PARSE_ERROR)
                            GetPostsResultError.NO_POST_EXIST -> _liveState.value = State.Failure(Error.NO_POST_EXIST)
                            GetPostsResultError.NO_SESSION -> _liveState.value = State.Failure(Error.NO_SESSION)
                        }
                    }
                }
            }.addTo(disposables)
    }

    fun reloadData() {
        posts = mutableListOf<PostData>()
        page = 0
        hasMore = true
        _liveIsLoading.value = false
        _livePosts.value = posts
        loadData()
    }

    fun addPost(post: PostData) {
        reloadData()
    }
}