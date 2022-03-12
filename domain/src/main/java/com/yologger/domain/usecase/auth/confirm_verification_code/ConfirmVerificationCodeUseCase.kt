package com.yologger.domain.usecase.auth.confirm_verification_code

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ConfirmVerificationCodeUseCase @Inject
constructor(
    private val authRepository: AuthRepository
) : ObservableUseCase<ConfirmVerificationCodeUseCase.Params, ConfirmVerificationCodeResult>() {
    data class Params(val email:String, val code: String)

    override fun call(params: Params?): Observable<ConfirmVerificationCodeResult> {
        params?.let {
            return Observable.create { emitter ->
                emitter.onNext(authRepository.confirmVerificationCode(it.email, it.code))
            }
        } ?: run {
            return Observable.just(
                ConfirmVerificationCodeResult.FAILURE(ConfirmVerificationCodeResultError.INVALID_PARAMETERS)
            )
        }
    }
}