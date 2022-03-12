package com.yologger.domain.usecase.auth.join

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class JoinUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : ObservableUseCase<JoinUseCase.Params, JoinResult>() {

    data class Params(val email: String, val name: String, val nickname: String, val password: String)

    override fun call(params: Params?): Observable<JoinResult> {
        params?.let { it
            return Observable.create { emitter ->
                emitter.onNext(authRepository.join(email = params.email, password = params.password, name = params.name, nickname = params.nickname))
            }
        } ?: run {
            return Observable.just(JoinResult.FAILURE(JoinResultError.INVALID_PARAMS))
        }
    }
}