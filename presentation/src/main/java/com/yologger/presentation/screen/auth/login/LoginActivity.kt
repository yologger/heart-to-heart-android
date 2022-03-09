package com.yologger.presentation.screen.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orhanobut.logger.Logger
import com.yologger.presentation.R
import com.yologger.presentation.component.LoadingDialog
import com.yologger.presentation.databinding.ActivityLoginBinding
import com.yologger.presentation.screen.auth.join.JoinViewModel
import com.yologger.presentation.screen.auth.verify_email.VerifyEmailActivity
import com.yologger.presentation.screen.main.MainActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {

    }

    private fun observeViewModel() {
        viewModel.liveIsLoading.observe(this) {
            if (it) {
                loadingDialog = LoadingDialog(this)
                loadingDialog?.show("Loading")
            } else {
                loadingDialog?.dismiss()
                loadingDialog = null
            }
        }

        viewModel.liveState.observe(this) {
            when(it) {
                is LoginViewModel.State.SUCCESS -> {
                    showToast("로그인 성공")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is LoginViewModel.State.FAILURE -> {
                    when(it.error) {
                        LoginViewModel.Error.INVALID_INPUT_VALUE -> showToast("Invalid input error")
                        LoginViewModel.Error.NETWORK_ERROR -> showToast("Network error")
                        LoginViewModel.Error.UNKNOWN_SERVER_ERROR -> showToast("Unknown server error")
                        LoginViewModel.Error.MEMBER_NOT_EXIST -> showToast("존재하지 않는 사용자입니다.")
                        LoginViewModel.Error.INVALID_PASSWORD -> showToast("잘못된 비밀번호입니다.")
                    }
                }
            }
        }
    }
}