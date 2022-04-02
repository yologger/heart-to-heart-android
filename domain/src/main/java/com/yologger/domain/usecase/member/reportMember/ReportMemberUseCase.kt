package com.yologger.domain.usecase.member.reportMember

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ReportMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<ReportMemberUseCase.Params, ReportMemberResult>() {
    data class Params(val targetId: Long)

    override fun call(params: Params?): Observable<ReportMemberResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(memberRepository.reportMember(it.targetId))
            }
        } ?: run {
            Observable.just(ReportMemberResult.Failure(ReportMemberResultError.INVALID_PARAMS))
        }
    }
}