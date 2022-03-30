package com.yologger.presentation.screen.main.home.user_detail.report_user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityReportUserBinding

class ReportUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_user)
        binding.lifecycleOwner = this
    }

    private fun initUI () {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}