package com.yologger.domain.usecase.member.fetch_member_info

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.PostRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FetchMemberInfoUseCase @Inject constructor(
    private val postRepository: PostRepository
) : ObservableUseCase<FetchMemberInfoUseCase.Params, FetchMemberInfoResult>() {

    data class Params(val data: String)

    override fun call(params: Params?): Observable<FetchMemberInfoResult> {
        return Observable.just(postRepository.fetchMemberInfo())
    }
}