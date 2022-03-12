package com.yologger.presentation.screen.auth.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.auth.login.LoginResultError
import com.yologger.domain.usecase.auth.login.LoginResult
import com.yologger.domain.usecase.auth.login.LoginUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    sealed class State {
        object SUCCESS: State()
        data class FAILURE(val error: Error): State()
    }

    enum class Error {
        NETWORK_ERROR,
        CLIENT_ERROR,
        MEMBER_NOT_EXIST,
        INVALID_PASSWORD,
        INVALID_PARAMS
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private val _liveIsLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    val liveEmail: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveEmailErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    private val liveIsEmailValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val livePassword: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val livePasswordErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    private val liveIsPasswordValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val liveAreAllInputsValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

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
        verifyAllValues()
    }

    fun onTextFieldPasswordChanged(text: CharSequence, start: Int, count: Int, after: Int) {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(text)

        if (text.length < 8) {
            livePasswordErrorMessage.value = "'Password' is too short."
            liveIsPasswordValid.value = false
        } else if(text.length > 20) {
            livePasswordErrorMessage.value = "'Password' is too long."
            liveIsPasswordValid.value = false
        } else {
            if (matcher.matches()) {
                livePasswordErrorMessage.value = ""
                liveIsPasswordValid.value = true
            } else {
                livePasswordErrorMessage.value = "Invalid password pattern."
                liveIsPasswordValid.value = false
            }
        }
        verifyAllValues()
    }

    private fun verifyAllValues() {
        liveAreAllInputsValid.value = liveIsEmailValid.value!! && liveIsPasswordValid.value!!
    }

    fun login() {
        val params = LoginUseCase.Params(email = liveEmail.value!!, password = livePassword.value!!)
        _liveIsLoading.value = true
        loginUseCase.execute(params)
            .subscribeBy {
                _liveIsLoading.value = false
                when(it) {
                    is LoginResult.SUCCESS -> _liveState.value = State.SUCCESS
                    is LoginResult.FAILURE -> {
                        when(it.error) {
                            LoginResultError.MEMBER_NOT_EXIST -> _liveState.value = State.FAILURE(Error.MEMBER_NOT_EXIST)
                            LoginResultError.INVALID_PASSWORD -> _liveState.value = State.FAILURE(Error.INVALID_PASSWORD)
                            LoginResultError.NETWORK_ERROR -> _liveState.value = State.FAILURE(Error.NETWORK_ERROR)
                            LoginResultError.CLIENT_ERROR -> _liveState.value = State.FAILURE(Error.CLIENT_ERROR)
                            LoginResultError.INVALID_PARAMS -> _liveState.value = State.FAILURE(Error.INVALID_PARAMS)
                        }
                    }
                }
            }.addTo(disposables)
    }
}