package com.yologger.domain.usecase.post.deletePost

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.PostRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) : ObservableUseCase<DeletePostUseCase.Params, DeletePostResult>() {
    data class Params(val postId: Long)
    override fun call(params: Params?): Observable<DeletePostResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(postRepository.deletePost(params.postId))
            }
        } ?: run {
            Observable.just(DeletePostResult.Failure(DeletePostResultError.CLIENT_ERROR))
        }
    }
}