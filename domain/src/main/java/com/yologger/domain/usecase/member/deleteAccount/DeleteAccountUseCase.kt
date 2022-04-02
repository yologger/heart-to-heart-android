package com.yologger.domain.usecase.member.deleteAccount

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.MemberRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) : ObservableUseCase<DeleteAccountUseCase.Params, DeleteAccountResult>() {

    data class Params(val data: String)

    override fun call(params: Params?): Observable<DeleteAccountResult> {
        return Observable.create { emitter ->
            emitter.onNext(memberRepository.deleteAccount())
        }
    }
}