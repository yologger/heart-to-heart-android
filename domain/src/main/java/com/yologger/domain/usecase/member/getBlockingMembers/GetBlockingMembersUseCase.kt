package com.yologger.domain.usecase.member.getBlockingMembers

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetBlockingMembersUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<GetBlockingMembersUseCase.Params, GetBlockingMembersResult>() {
    data class Params(val data: String)

    override fun call(params: Params?): Observable<GetBlockingMembersResult> {
        return Observable.create { emitter ->
            emitter.onNext(memberRepository.getBlockingMembers())
        }
    }
}