package com.yologger.presentation.screen.auth.verify_email

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityVerifyEmailBinding
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyEmailActivity : AppCompatActivity() {

    private val viewModel: VerifyEmailViewModel by viewModels()
    private lateinit var binding: ActivityVerifyEmailBinding

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
                is VerifyEmailViewModel.State.SUCCESS -> {
                    val builder = AlertDialog.Builder(this@VerifyEmailActivity)
                    val alertDialog = builder
                        .setTitle("인증코드가 발송되었습니다.")
                        .setMessage("메일로 발송된 인증 번호를 입력해주세요")
                        .setPositiveButton("OK") { _, _ -> }
                        .create()

                    alertDialog.show()
                }
                is VerifyEmailViewModel.State.FAILURE -> handleFailure(it.error)
            }
        }
    }

    private fun handleFailure(error: VerifyEmailViewModel.Error) {
        when (error) {
            VerifyEmailViewModel.Error.UNKNOWN_SERVER_ERROR -> showToast("Unknown server error")
            VerifyEmailViewModel.Error.NETWORK_ERROR -> showToast("Network error")
            VerifyEmailViewModel.Error.MEMBER_ALREADY_EXIST -> showToast("User already exists")
            VerifyEmailViewModel.Error.INVALID_INPUT_VALUE -> showToast("Invalid inputs")
        }
    }


}