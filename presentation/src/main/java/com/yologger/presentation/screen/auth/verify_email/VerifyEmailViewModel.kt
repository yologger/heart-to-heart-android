package com.yologger.presentation.screen.auth.verify_email

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeUseCase
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeError
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val emailVerificationCodeUseCase: EmailVerificationCodeUseCase,
    private val confirmVerificationCodeUseCase: ConfirmVerificationCodeUseCase
) : BaseViewModel() {

    sealed class State {
        object SUCCESS: State()
        data class FAILURE(val error: Error): State()
    }

    enum class Error {
        UNKNOWN_SERVER_ERROR,
        NETWORK_ERROR,
        MEMBER_ALREADY_EXIST,
        INVALID_INPUT_VALUE
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    val liveEmail: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveEmailErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveIsEmailValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val liveHasVerificationAlreadyRequested: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val liveCode: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveCodeErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveIsCodeValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    fun emailVerificationCode() {
        val params = EmailVerificationCodeUseCase.Params(email = liveEmail.value!!)
        emailVerificationCodeUseCase.execute(params)
            .subscribeBy(
                onNext = { result ->
                    when(result) {
                        is EmailVerificationCodeResult.SUCCESS -> {
                            liveHasVerificationAlreadyRequested.value = true
                            _liveState.value = State.SUCCESS
                        }
                        is EmailVerificationCodeResult.FAILURE -> {
                           when (result.error) {
                               EmailVerificationCodeError.INVALID_INPUT_VALUE -> _liveState.value = State.FAILURE(Error.INVALID_INPUT_VALUE)
                               EmailVerificationCodeError.MEMBER_ALREADY_EXISTS -> _liveState.value = State.FAILURE(Error.MEMBER_ALREADY_EXIST)
                               EmailVerificationCodeError.NETWORK_ERROR -> _liveState.value = State.FAILURE(Error.NETWORK_ERROR)
                               EmailVerificationCodeError.UNKNOWN_SERVER_ERROR -> _liveState.value = State.FAILURE(Error.UNKNOWN_SERVER_ERROR)
                           } 
                        }
                    }                         
                },
                onError = {},
                onComplete = {}
            ).addTo(disposables)
    }

    fun confirmVerificationCode() {
        
    }

    fun onTextFieldEmailChanged(text: CharSequence, start: Int, count: Int, after: Int) {
        if (TextUtils.isEmpty(text)) {
            liveEmailErrorMessage.value = "'Email' field must not be empty."
            liveIsEmailValid.value = false
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                liveEmailErrorMessage.value = "Invalid email format."
                liveIsEmailValid.value = false
            } else {
                liveEmailErrorMessage.value = ""
                liveIsEmailValid.value = true
            }
        }
    }
}