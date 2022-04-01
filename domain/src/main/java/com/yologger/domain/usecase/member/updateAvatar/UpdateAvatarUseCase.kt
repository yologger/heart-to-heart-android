package com.yologger.domain.usecase.member.updateAvatar

import android.net.Uri
import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class UpdateAvatarUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<UpdateAvatarUseCase.Params, UpdateAvatarResult>() {
    data class Params(val imageUri: Uri)

    override fun call(params: Params?): Observable<UpdateAvatarResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(memberRepository.updateAvatar(it.imageUri))
            }
        } ?: run {
            Observable.just(UpdateAvatarResult.Failure(UpdateAvatarResultError.INVALID_PARAMS))
        }
    }
}