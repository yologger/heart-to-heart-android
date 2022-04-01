package com.yologger.presentation.screen.main.home.userDetail.reportUser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityReportUserBinding
import com.yologger.presentation.util.navigateToLogin
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportUserActivity : AppCompatActivity() {

    private val viewModel: ReportUserViewModel by viewModels()
    private lateinit var binding: ActivityReportUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_user)
        binding.lifecycleOwner = this
    }

    private fun initUI () {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.buttonReport.setOnClickListener {
            val memberId = intent.getLongExtra("member_id", 0)
            viewModel.reportMember(memberId)
        }
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(this) {
            when(it) {
                is ReportUserViewModel.State.Success -> {
                    showToast("신고가 접수되었습니다.")
                    finish()
                }
                is ReportUserViewModel.State.Failure -> {
                    when(it.error) {
                        ReportUserViewModel.Error.INVALID_PARAMS -> showToast("Client Error")
                        ReportUserViewModel.Error.CLIENT_ERROR -> showToast("Client Error")
                        ReportUserViewModel.Error.NETWORK_ERROR -> {
                            showToast("Network Error")
                            navigateToLogin()
                        }
                        ReportUserViewModel.Error.JSON_PARSE_ERROR -> {
                            showToast("Json Parsing Error")
                            navigateToLogin()
                        }
                        ReportUserViewModel.Error.NO_SESSION -> {
                            navigateToLogin()
                        }
                    }
                }
            }
        }
    }
}