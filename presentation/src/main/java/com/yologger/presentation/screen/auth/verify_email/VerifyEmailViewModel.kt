package com.yologger.presentation.screen.auth.verify_email

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeError
import com.yologger.domain.usecase.confirm_verification_code.ConfirmVerificationCodeResult
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
        object EMAIL_VERIFICATION_CODE_SUCCESS: State()
        data class EMAIL_VERIFICATION_CODE_FAILURE(val error: EMAIL_VERIFICATION_CODE_ERROR): State()
        data class CONFIRM_VERIFICATION_CODE_SUCCESS(val email: String): State()
        data class CONFIRM_VERIFICATION_CODE_FAILURE(val error: CONFIRM_VERIFICATION_CODE_ERROR): State()
    }

    enum class EMAIL_VERIFICATION_CODE_ERROR {
        UNKNOWN_SERVER_ERROR,
        NETWORK_ERROR,
        MEMBER_ALREADY_EXIST,
        INVALID_INPUT_VALUE,
    }

    enum class CONFIRM_VERIFICATION_CODE_ERROR {
        UNKNOWN_SERVER_ERROR,
        NETWORK_ERROR,
        INVALID_EMAIL,
        EXPIRED_VERIFICATION_CODE,
        INVALID_VERIFICATION_CODE,
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private val _liveIsLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    val liveEmail: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveEmailErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveIsEmailValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveHasVerificationAlreadyRequested: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveCode: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveIsCodeValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    fun emailVerificationCode() {
        val params = EmailVerificationCodeUseCase.Params(email = liveEmail.value!!.trim())
        _liveIsLoading.value = true
        emailVerificationCodeUseCase.execute(params)
            .subscribeBy(
                onNext = { result ->
                    _liveIsLoading.value = false
                    when(result) {
                        is EmailVerificationCodeResult.SUCCESS -> {
                            liveHasVerificationAlreadyRequested.value = true
                            _liveState.value = State.EMAIL_VERIFICATION_CODE_SUCCESS
                        }
                        is EmailVerificationCodeResult.FAILURE -> {
                           when (result.error) {
                               EmailVerificationCodeError.INVALID_INPUT_VALUE -> _liveState.value = State.EMAIL_VERIFICATION_CODE_FAILURE(EMAIL_VERIFICATION_CODE_ERROR.INVALID_INPUT_VALUE)
                               EmailVerificationCodeError.MEMBER_ALREADY_EXISTS -> _liveState.value = State.EMAIL_VERIFICATION_CODE_FAILURE(EMAIL_VERIFICATION_CODE_ERROR.MEMBER_ALREADY_EXIST)
                               EmailVerificationCodeError.NETWORK_ERROR -> _liveState.value = State.EMAIL_VERIFICATION_CODE_FAILURE(EMAIL_VERIFICATION_CODE_ERROR.NETWORK_ERROR)
                               EmailVerificationCodeError.UNKNOWN_SERVER_ERROR -> _liveState.value = State.EMAIL_VERIFICATION_CODE_FAILURE(EMAIL_VERIFICATION_CODE_ERROR.UNKNOWN_SERVER_ERROR)
                           } 
                        }
                    }                         
                },
                onError = { _liveIsLoading.value = false },
                onComplete = { _liveIsLoading.value = false }
            ).addTo(disposables)
    }

    fun confirmVerificationCode() {
        val params = ConfirmVerificationCodeUseCase.Params(liveEmail.value!!.trim(), liveCode.value!!.trim())
        _liveIsLoading.value = true
        confirmVerificationCodeUseCase.execute(params)
            .subscribeBy { result ->
                _liveIsLoading.value = false
                when(result) {
                    is ConfirmVerificationCodeResult.SUCCESS -> {
                        _liveState.value = State.CONFIRM_VERIFICATION_CODE_SUCCESS(email = liveEmail.value!!.trim())
                    }
                    is ConfirmVerificationCodeResult.FAILURE -> {
                        when (result.error) {
                            ConfirmVerificationCodeError.EXPIRED_VERIFICATION_CODE -> _liveState.value = State.CONFIRM_VERIFICATION_CODE_FAILURE(CONFIRM_VERIFICATION_CODE_ERROR.EXPIRED_VERIFICATION_CODE)
                            ConfirmVerificationCodeError.INVALID_EMAIL -> _liveState.value = State.CONFIRM_VERIFICATION_CODE_FAILURE(CONFIRM_VERIFICATION_CODE_ERROR.INVALID_EMAIL)
                            ConfirmVerificationCodeError.INVALID_VERIFICATION_CODE -> _liveState.value = State.CONFIRM_VERIFICATION_CODE_FAILURE(CONFIRM_VERIFICATION_CODE_ERROR.INVALID_VERIFICATION_CODE)
                            ConfirmVerificationCodeError.NETWORK_ERROR ->  _liveState.value = State.CONFIRM_VERIFICATION_CODE_FAILURE(CONFIRM_VERIFICATION_CODE_ERROR.NETWORK_ERROR)
                            ConfirmVerificationCodeError.UNKNOWN_SERVER_ERROR ->  _liveState.value = State.CONFIRM_VERIFICATION_CODE_FAILURE(CONFIRM_VERIFICATION_CODE_ERROR.UNKNOWN_SERVER_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
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

    fun onTextFieldCodeChanged(text: CharSequence, start: Int, count: Int, after: Int) {
        if (text.trim().length < 1) {
            liveIsCodeValid.value = false
        } else {
            liveIsCodeValid.value = true
        }
    }
}