package com.yologger.presentation.screen.auth.verify_email

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.component.LoadingDialog
import com.yologger.presentation.databinding.ActivityVerifyEmailBinding
import com.yologger.presentation.screen.auth.join.JoinActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyEmailActivity : AppCompatActivity() {

    private val viewModel: VerifyEmailViewModel by viewModels()
    private lateinit var binding: ActivityVerifyEmailBinding
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_email)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.activity_verify_email_menu_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.activity_verify_email_menu_toolbar_action_close -> {
                    finish()
                    true
                }
            }
            false
        }
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(this) {
            when (it) {
                is VerifyEmailViewModel.State.EMAIL_VERIFICATION_CODE_SUCCESS -> {
                    val builder = AlertDialog.Builder(this@VerifyEmailActivity)
                    val alertDialog = builder
                        .setTitle("이메일로 인증코드가 발송되었습니다.")
                        .setMessage("인증번호의 유효 시간은 5분입니다.")
                        .setPositiveButton("OK") { _, _ -> }
                        .create()
                    alertDialog.show()
                }
                is VerifyEmailViewModel.State.EMAIL_VERIFICATION_CODE_FAILURE -> {
                    when(it.error) {
                        VerifyEmailViewModel.EMAIL_VERIFICATION_CODE_ERROR.NETWORK_ERROR -> showToast("Network Error")
                        VerifyEmailViewModel.EMAIL_VERIFICATION_CODE_ERROR.MAIL_ERROR -> showToast("Mail Server Error")
                        VerifyEmailViewModel.EMAIL_VERIFICATION_CODE_ERROR.CLIENT_ERROR -> showToast("Client Request Error")
                        VerifyEmailViewModel.EMAIL_VERIFICATION_CODE_ERROR.MEMBER_ALREADY_EXIST -> showToast("Member Already Exists")
                        VerifyEmailViewModel.EMAIL_VERIFICATION_CODE_ERROR.INVALID_PARAMS -> showToast("Invalid Parameters")
                    }
                }
                is VerifyEmailViewModel.State.CONFIRM_VERIFICATION_CODE_SUCCESS -> {
                    val intent = Intent(this, JoinActivity::class.java)
                    intent.putExtra("email", it.email)
                    startActivity(intent)
                }

                is VerifyEmailViewModel.State.CONFIRM_VERIFICATION_CODE_FAILURE -> {
                    when(it.error) {
                        VerifyEmailViewModel.CONFIRM_VERIFICATION_CODE_ERROR.CLIENT_ERROR -> showToast("Client Request Error")
                        VerifyEmailViewModel.CONFIRM_VERIFICATION_CODE_ERROR.NETWORK_ERROR -> showToast("Network Error")
                        VerifyEmailViewModel.CONFIRM_VERIFICATION_CODE_ERROR.INVALID_EMAIL -> showToast("Invalid Email")
                        VerifyEmailViewModel.CONFIRM_VERIFICATION_CODE_ERROR.EXPIRED_VERIFICATION_CODE -> showToast("Expired Verification Code")
                        VerifyEmailViewModel.CONFIRM_VERIFICATION_CODE_ERROR.INVALID_VERIFICATION_CODE -> showToast("Invalid Verification Code")
                    }
                }
            }
        }

        viewModel.liveIsLoading.observe(this) {
            if (it) {
                loadingDialog = LoadingDialog(this)
                loadingDialog?.show("Loading")
            } else {
                loadingDialog?.dismiss()
                loadingDialog = null
            }
        }
    }
}