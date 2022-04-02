package com.yologger.presentation.screen.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.member.blockMember.BlockMemberResult
import com.yologger.domain.usecase.member.blockMember.BlockMemberResultError
import com.yologger.domain.usecase.member.blockMember.BlockMemberUseCase
import com.yologger.domain.usecase.post.getPosts.GetPostsResult
import com.yologger.domain.usecase.post.getPosts.GetPostsResultError
import com.yologger.domain.usecase.post.getPosts.GetPostsUseCase
import com.yologger.domain.usecase.post.getPosts.PostData
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val blockMemberUseCase: BlockMemberUseCase
) : BaseViewModel() {

    sealed class State {
        object GetPostsSuccess: State()
        data class GetPostsFailure(val error: GetPostsError): State()
        object BlockMemberSuccess: State()
        data class BlockMemberFailure(val error: BlockMemberError): State()
    }

    enum class GetPostsError {
        NETWORK_ERROR,
        CLIENT_ERROR,
        JSON_PARSE_ERROR,
        NO_POSTS_EXIST,
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

    fun addPost(post: PostData) {
        reloadData()
    }

    fun blockMember(memberId: Long) {
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