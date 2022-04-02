package com.yologger.presentation.screen.main.home.userDetail.blockUser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityBlockUserBinding

class BlockUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlockUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_user)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_block_user)
        binding.lifecycleOwner = this


    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_black_24_filled_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}