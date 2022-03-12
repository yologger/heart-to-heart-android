package com.yologger.domain.usecase.auth.logout

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : ObservableUseCase<LogoutUseCase.Params, LogoutResult>() {

    data class Params(val data: String)

    override fun call(params: Params?): Observable<LogoutResult> {
        return Observable.create { emitter ->
            emitter.onNext(authRepository.logout())
        }
    }
}