package com.yologger.presentation.screen.auth.verifyEmail

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.auth.confirmVerificationCode.ConfirmVerificationCodeResult
import com.yologger.domain.usecase.auth.confirmVerificationCode.ConfirmVerificationCodeResultError
import com.yologger.domain.usecase.auth.confirmVerificationCode.ConfirmVerificationCodeUseCase
import com.yologger.domain.usecase.auth.emailVerificationCode.EmailVerificationCodeResult
import com.yologger.domain.usecase.auth.emailVerificationCode.EmailVerificationCodeResultError
import com.yologger.domain.usecase.auth.emailVerificationCode.EmailVerificationCodeUseCase
import com.yologger.presentation.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val emailVerificationCodeUseCase: EmailVerificationCodeUseCase,
    private val confirmVerificationCodeUseCase: ConfirmVerificationCodeUseCase
) : BaseViewModel() {

    sealed class State {
        object EmailVerificationCodeSuccess: State()
        data class EmailVerificationCodeFailure(val error: EmailVerificationCodeError): State()
        data class ConfirmVerificationCodeSuccess(val email: String): State()
        data class ConfirmVerificationCodeFailure(val error: ConfirmVerificationCodeError): State()
    }

    enum class EmailVerificationCodeError {
        NETWORK_ERROR,
        MAIL_ERROR,
        CLIENT_ERROR,
        MEMBER_ALREADY_EXIST,
        INVALID_PARAMS,
        JSON_PARSE_ERROR
    }

    enum class ConfirmVerificationCodeError {
        NETWORK_ERROR,
        CLIENT_ERROR,
        INVALID_EMAIL,
        EXPIRED_VERIFICATION_CODE,
        INVALID_VERIFICATION_CODE,
        JSON_PARSE_ERROR
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
            .subscribeBy { result ->
                _liveIsLoading.value = false
                when(result) {
                    is EmailVerificationCodeResult.SUCCESS -> {
                        liveHasVerificationAlreadyRequested.value = true
                        _liveState.value = State.EmailVerificationCodeSuccess
                    }
                    is EmailVerificationCodeResult.FAILURE -> {
                        when (result.error) {
                            EmailVerificationCodeResultError.MAIL_ERROR -> _liveState.value = State.EmailVerificationCodeFailure(EmailVerificationCodeError.MAIL_ERROR)
                            EmailVerificationCodeResultError.CLIENT_ERROR -> _liveState.value = State.EmailVerificationCodeFailure(EmailVerificationCodeError.CLIENT_ERROR)
                            EmailVerificationCodeResultError.MEMBER_ALREADY_EXIST -> _liveState.value = State.EmailVerificationCodeFailure(EmailVerificationCodeError.MEMBER_ALREADY_EXIST)
                            EmailVerificationCodeResultError.INVALID_PARAMS -> _liveState.value = State.EmailVerificationCodeFailure(EmailVerificationCodeError.INVALID_PARAMS)
                            EmailVerificationCodeResultError.NETWORK_ERROR -> _liveState.value = State.EmailVerificationCodeFailure(EmailVerificationCodeError.NETWORK_ERROR)
                            EmailVerificationCodeResultError.JSON_PARSE_ERROR -> _liveState.value = State.EmailVerificationCodeFailure(EmailVerificationCodeError.JSON_PARSE_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
    }

    fun confirmVerificationCode() {
        val params = ConfirmVerificationCodeUseCase.Params(liveEmail.value!!.trim(), liveCode.value!!.trim())
        _liveIsLoading.value = true
        confirmVerificationCodeUseCase.execute(params)
            .subscribeBy { result ->
                _liveIsLoading.value = false
                when(result) {
                    is ConfirmVerificationCodeResult.SUCCESS -> {
                        _liveState.value = State.ConfirmVerificationCodeSuccess(email = liveEmail.value!!.trim())
                    }
                    is ConfirmVerificationCodeResult.FAILURE -> {
                        when (result.error) {
                            ConfirmVerificationCodeResultError.EXPIRED_VERIFICATION_CODE -> _liveState.value = State.ConfirmVerificationCodeFailure(ConfirmVerificationCodeError.EXPIRED_VERIFICATION_CODE)
                            ConfirmVerificationCodeResultError.INVALID_EMAIL -> _liveState.value = State.ConfirmVerificationCodeFailure(ConfirmVerificationCodeError.INVALID_EMAIL)
                            ConfirmVerificationCodeResultError.INVALID_VERIFICATION_CODE -> _liveState.value = State.ConfirmVerificationCodeFailure(ConfirmVerificationCodeError.INVALID_VERIFICATION_CODE)
                            ConfirmVerificationCodeResultError.NETWORK_ERROR ->  _liveState.value = State.ConfirmVerificationCodeFailure(ConfirmVerificationCodeError.NETWORK_ERROR)
                            ConfirmVerificationCodeResultError.CLIENT_ERROR ->  _liveState.value = State.ConfirmVerificationCodeFailure(ConfirmVerificationCodeError.CLIENT_ERROR)
                            ConfirmVerificationCodeResultError.JSON_PARSE_ERROR ->  _liveState.value = State.ConfirmVerificationCodeFailure(ConfirmVerificationCodeError.JSON_PARSE_ERROR)
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
        liveIsCodeValid.value = text.trim().length >= 1
    }
}