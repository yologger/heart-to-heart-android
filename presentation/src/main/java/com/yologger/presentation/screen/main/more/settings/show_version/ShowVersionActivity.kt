package com.yologger.presentation.screen.main.more.settings.show_version

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityShowVersionBinding

class ShowVersionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowVersionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_version)
        binding.lifecycleOwner = this
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_24_white)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.textViewVersion.text = "현재 버전 ${packageManager.getPackageInfo(packageName, 0).versionName}"
        Glide.with(this@ShowVersionActivity)
            .load(R.drawable.logo_background_white)
            .centerCrop()
            .into(binding.imageViewLogo)
    }
}