package com.yologger.domain.usecase.email_verification_code

import com.yologger.domain.base.ObservableUseCase
import com.yologger.domain.repository.AuthRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class EmailVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : ObservableUseCase<EmailVerificationCodeUseCase.Params, EmailVerificationCodeResult>() {

    data class Params(val email: String)

    override fun call(params: Params?): Observable<EmailVerificationCodeResult> {
        params?.let { it
            return Observable.create { emitter ->
                emitter.onNext(authRepository.emailVerificationCode(it.email))
            }
        } ?: run {
            return Observable.just(EmailVerificationCodeResult.FAILURE(EmailVerificationCodeError.INVALID_INPUT_VALUE))
        }
    }
}