package com.yologger.domain.usecase.member.unblockMember

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class UnblockMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<UnblockMemberUseCase.Params, UnblockMemberResult>() {

    data class Params(val memberId: Long)

    override fun call(params: Params?): Observable<UnblockMemberResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(memberRepository.unblockMember(it.memberId))
            }
        } ?: run {
            Observable.just(UnblockMemberResult.Failure(UnblockMemberResultError.INVALID_PARAMS))
        }
    }
}