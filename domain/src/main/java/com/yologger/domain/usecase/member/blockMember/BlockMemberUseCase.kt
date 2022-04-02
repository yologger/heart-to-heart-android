package com.yologger.domain.usecase.member.blockMember

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class BlockMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<BlockMemberUseCase.Params, BlockMemberResult>() {

    data class Params(val memberId: Long)

    override fun call(params: Params?): Observable<BlockMemberResult> {
        return params?.let {
            Observable.create { emitter ->
                emitter.onNext(memberRepository.blockMember(it.memberId))
            }
        } ?: run {
            Observable.just(BlockMemberResult.Failure(BlockMemberResultError.INVALID_PARAMS))
        }
    }
}