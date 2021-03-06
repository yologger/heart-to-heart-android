package com.yologger.domain.usecase.post.getPosts

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.PostRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) : ObservableUseCase<GetPostsUseCase.Params, GetPostsResult>() {
    data class Params(val postId: Long?, val page: Int, val size: Int)

    override fun call(params: Params?): Observable<GetPostsResult> {
        return params?.let { _params ->
            Observable.create { emitter ->
                _params.postId?.let { _postId ->
                    emitter.onNext(postRepository.getPosts(id = _postId, size = _params.size, page = _params.page))
                } ?: run {
                    emitter.onNext(postRepository.getPosts(id = null, size = _params.size, page = _params.page))
                }
            }
        } ?: run {
            Observable.just(GetPostsResult.Failure(GetPostsResultError.CLIENT_ERROR))
        }
    }
}