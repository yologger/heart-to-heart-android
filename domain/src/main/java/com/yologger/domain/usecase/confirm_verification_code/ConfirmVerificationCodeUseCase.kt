package com.yologger.domain.usecase.confirm_verification_code

import com.yologger.domain.repository.AuthRepository
import javax.inject.Inject

class ConfirmVerificationCodeUseCase @Inject
constructor(
    private val authRepository: AuthRepository
) {
}