package com.yologger.domain.usecase.auth.login

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : ObservableUseCase<LoginUseCase.Params, LoginResult>() {

    data class Params(val email: String, val password: String)

    override fun call(params: Params?): Observable<LoginResult> {
        params?.let {
            return Observable.create { emitter ->
                emitter.onNext(authRepository.login(email = params.email, password = params.password))
            }
        } ?: run {
            return Observable.just(LoginResult.FAILURE(LoginResultError.INVALID_PARAMS))
        }
    }
}