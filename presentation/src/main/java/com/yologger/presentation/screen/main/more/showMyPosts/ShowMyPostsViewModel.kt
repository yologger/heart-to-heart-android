package com.yologger.presentation.screen.main.more.showMyPosts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.post.deletePost.DeletePostResult
import com.yologger.domain.usecase.post.deletePost.DeletePostResultError
import com.yologger.domain.usecase.post.deletePost.DeletePostUseCase
import com.yologger.domain.usecase.post.getAllPosts.PostData
import com.yologger.domain.usecase.post.getPosts.GetPostsResult
import com.yologger.domain.usecase.post.getPosts.GetPostsResultError
import com.yologger.domain.usecase.post.getPosts.GetPostsUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class ShowMyPostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : BaseViewModel() {

    sealed class State {
        object GetPostsSuccess : State()
        data class GetPostsFailure(val error: GetPostsError) : State()
        data class DeletePostFailure(val error: DeletePostError): State()
    }

    enum class GetPostsError {
        NETWORK_ERROR,
        CLIENT_ERROR,
        JSON_PARSE_ERROR,
        NO_POSTS_EXIST,
        NO_SESSION
    }

    enum class DeletePostError {
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

    private var posts = mutableListOf<PostData>()
    private var _livePosts: MutableLiveData<MutableList<PostData>> = MutableLiveData(posts)
    val livePosts = _livePosts

    private val _liveIsLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    init {
        loadData()
    }

    fun loadData() {
        if (!hasMore) { return }
        _liveIsLoading.value = true

        val params = GetPostsUseCase.Params(postId = null, size = size, page = page)
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
                            GetPostsResultError.NETWORK_ERROR -> _liveState.value = State.GetPostsFailure(GetPostsError.NETWORK_ERROR)
                            GetPostsResultError.CLIENT_ERROR, GetPostsResultError.INVALID_PARAMS -> _liveState.value = State.GetPostsFailure(GetPostsError.CLIENT_ERROR)
                            GetPostsResultError.JSON_PARSE_ERROR -> _liveState.value = State.GetPostsFailure(GetPostsError.JSON_PARSE_ERROR)
                            GetPostsResultError.NO_POSTS_EXIST -> _liveState.value = State.GetPostsFailure(GetPostsError.NO_POSTS_EXIST)
                            GetPostsResultError.NO_SESSION -> _liveState.value = State.GetPostsFailure(GetPostsError.NO_SESSION)
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

    fun deletePost(postId: Long) {
        val params = DeletePostUseCase.Params(postId = postId)
        deletePostUseCase.execute(params)
            .subscribeBy {
                when(it) {
                    is DeletePostResult.Success -> {
                        reloadData()
                    }
                    is DeletePostResult.Failure -> {
                        when(it.error) {
                            DeletePostResultError.JSON_PARSE_ERROR -> _liveState.value = State.DeletePostFailure(DeletePostError.JSON_PARSE_ERROR)
                            DeletePostResultError.NO_POST_EXIST -> _liveState.value = State.DeletePostFailure(DeletePostError.NO_POST_EXIST)
                            DeletePostResultError.NO_SESSION -> _liveState.value = State.DeletePostFailure(DeletePostError.NO_SESSION)
                            DeletePostResultError.CLIENT_ERROR, DeletePostResultError.INVALID_PARAMS -> _liveState.value = State.DeletePostFailure(DeletePostError.CLIENT_ERROR)
                            DeletePostResultError.FILE_UPLOAD_ERROR -> reloadData()
                            DeletePostResultError.NETWORK_ERROR -> _liveState.value = State.DeletePostFailure(DeletePostError.NETWORK_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
    }
}