package com.yologger.presentation.screen.main.home.user_detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityUserDetailBinding
import com.yologger.presentation.screen.main.home.user_detail.report_user.ReportUserActivity
import com.yologger.presentation.util.showToast
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
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.activity_user_detail_toolbar_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.activity_user_detail_toolbar_menu_action_report -> {
                    val intent = Intent(this@UserDetailActivity, ReportUserActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.activity_user_detail_toolbar_menu_action_block -> {
                    val builder = AlertDialog.Builder(this@UserDetailActivity)
                    val alertDialog = builder
                        .setTitle("이 사용자 차단하기")
                        .setMessage("이 사용자의 모든 게시글을 보지 않으시겠습니까?")
                        .setPositiveButton("네, 안볼래요.") { _, _ ->
                            showToast("이 사용자의 글이 더 이상 보이지 않아요.")
                        }
                        .setNegativeButton("취소") { _, _ -> }
                        .create()
                    alertDialog.show()
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