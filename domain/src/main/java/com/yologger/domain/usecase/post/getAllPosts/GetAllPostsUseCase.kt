package com.yologger.domain.usecase.post.getAllPosts

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.PostRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) : ObservableUseCase<GetAllPostsUseCase.Params, GetAllPostsResult>() {
    data class Params(val page: Int, val size: Int)

    override fun call(params: Params?): Observable<GetAllPostsResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(postRepository.getAllPosts(size = it.size, page = it.page))
            }
        } ?: run {
            Observable.just(GetAllPostsResult.Failure(GetAllPostsResultError.CLIENT_ERROR))
        }
    }
}