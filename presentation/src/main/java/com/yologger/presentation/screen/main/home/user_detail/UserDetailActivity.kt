package com.yologger.presentation.screen.main.home.user_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        binding.lifecycleOwner = this
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_close_outlined_white_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.activity_user_detail_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.activity_register_post_menu_toolbar_action_report -> {
                    true
                }
                R.id.activity_register_post_menu_toolbar_action_block -> {
                    true
                }
                else -> {
                    false
                }
            }
            false
        }
    }
}