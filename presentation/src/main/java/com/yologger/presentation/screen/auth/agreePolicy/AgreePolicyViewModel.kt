package com.yologger.presentation.screen.auth.agreePolicy

import androidx.lifecycle.MutableLiveData
import com.yologger.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgreePolicyViewModel @Inject constructor(

) : BaseViewModel() {
    val liveIsTermsChecked: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val liveIsPolicyChecked: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
}