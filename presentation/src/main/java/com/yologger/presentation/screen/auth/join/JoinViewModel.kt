package com.yologger.presentation.screen.auth.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.domain.usecase.join.JoinError
import com.yologger.domain.usecase.join.JoinResult
import com.yologger.domain.usecase.join.JoinUseCase
import com.yologger.presentation.screen.base.BaseViewModel
import com.yologger.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val joinUseCase: JoinUseCase
) : BaseViewModel() {

    sealed class State {
        object SUCCESS: State()
        data class FAILURE(val error: Error): State()
    }

    enum class Error {
        NETWORK_ERROR,
        UNKNOWN_SERVER_ERROR,
        INVALID_INPUT_VALUE,
        MEMBER_ALREADY_EXISTS
    }

    private val _liveState = SingleLiveEvent<State>()
    val liveState = _liveState

    private val _liveIsLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsLoading: LiveData<Boolean> get() = _liveIsLoading

    val liveEmail: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }

    val livePassword: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val livePasswordErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    private val liveIsPasswordValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val liveName: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveNameErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    private val liveIsNameValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val liveNickname: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val liveNicknameErrorMessage: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    private val liveIsNicknameValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    val liveAreAllInputsValid: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }

    fun onTextFieldNameChanged(text: CharSequence, start: Int, count: Int, after: Int) {
        if (text.length > 20) {
            liveNameErrorMessage.value = "'Full Name' is too long."
            liveIsNameValid.value = false
        } else if (text.length < 3) {
            liveNameErrorMessage.value = "'Full Name' is too short."
            liveIsNameValid.value = false
        } else {
            liveNameErrorMessage.value = ""
            liveIsNameValid.value = true
        }
        verifyAllValues()
    }

    fun onTextFieldNicknameChanged(text: CharSequence, start: Int, count: Int, after: Int) {
        if (text.length > 20) {
            liveNicknameErrorMessage.value = "'Nickname' is too long."
            liveIsNicknameValid.value = false
        } else if (text.length < 3) {
            liveNicknameErrorMessage.value = "'Nickname' is too short."
            liveIsNicknameValid.value = false
        } else {
            liveNicknameErrorMessage.value = ""
            liveIsNicknameValid.value = true
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
        liveAreAllInputsValid.value = liveIsNameValid.value!! && liveIsNicknameValid.value!! && liveIsPasswordValid.value!!
    }

    fun join() {
        val params = JoinUseCase.Params(email = liveEmail.value!!, name = liveName.value!!, password = livePassword.value!!, nickname = liveNickname.value!!)
        _liveIsLoading.value = true
        joinUseCase.execute(params)
            .subscribeBy { result ->
                _liveIsLoading.value = false
                when(result) {
                    is JoinResult.SUCCESS -> {
                        _liveState.value = State.SUCCESS
                    }
                    is JoinResult.FAILURE -> {
                        when(result.error) {
                            JoinError.MEMBER_ALREADY_EXISTS -> _liveState.value = State.FAILURE(Error.MEMBER_ALREADY_EXISTS)
                            JoinError.INVALID_INPUT_VALUE -> _liveState.value = State.FAILURE(Error.INVALID_INPUT_VALUE)
                            JoinError.NETWORK_ERROR -> _liveState.value = State.FAILURE(Error.NETWORK_ERROR)
                            JoinError.UNKNOWN_SERVER_ERROR -> _liveState.value = State.FAILURE(Error.UNKNOWN_SERVER_ERROR)
                        }
                    }
                }
            }.addTo(disposables)
    }
}