package com.yologger.domain.usecase.post.get_posts

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.PostRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) : ObservableUseCase<GetPostsUseCase.Params, GetPostsResult>() {
    data class Params(val page: Int, val size: Int)

    override fun call(params: Params?): Observable<GetPostsResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(postRepository.getPosts(size = it.size, page = it.page))
            }
        } ?: run {
            Observable.just(GetPostsResult.Failure(GetPostsResultError.CLIENT_ERROR))
        }
    }
}