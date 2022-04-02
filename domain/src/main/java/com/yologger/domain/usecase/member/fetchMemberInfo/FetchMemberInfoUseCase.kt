package com.yologger.domain.usecase.member.fetchMemberInfo

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FetchMemberInfoUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<FetchMemberInfoUseCase.Params, FetchMemberInfoResult>() {

    data class Params(val memberId: Long)

    override fun call(params: Params?): Observable<FetchMemberInfoResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(memberRepository.fetchMemberInfo(it.memberId))
            }
        } ?: run {
            Observable.create { emitter ->
                emitter.onNext(memberRepository.fetchMemberInfo())
            }
        }

    }
}