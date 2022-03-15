package com.yologger.domain.usecase.post.register_post

import android.net.Uri
import com.orhanobut.logger.Logger
import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.PostRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RegisterPostUseCase @Inject constructor(
       private val postRepository: PostRepository
) : ObservableUseCase<RegisterPostUseCase.Params, RegisterPostResult>() {
    
    data class Params(val content: String, val imageUris: List<Uri>)

    override fun call(params: Params?): Observable<RegisterPostResult> {
        return params?.run {
            Observable.create { emitter ->
                emitter.onNext(postRepository.registerPost(params.content, params.imageUris))
            }
        } ?: run {
            Observable.just(RegisterPostResult.FAILURE(RegisterPostResultError.INVALID_PARAMS))
        }
    }
}