package com.yologger.domain.usecase.member.getMeId

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetMeIdUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<GetMeIdUseCase.Params, GetMeIdResult>() {
    data class Params(val data: String)

    override fun call(params: Params?): Observable<GetMeIdResult> {
        return Observable.create { emitter ->
            emitter.onNext(memberRepository.getMeId())
        }
    }
}