package com.yologger.presentation.screen.auth.join

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yologger.presentation.R

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        initBinding()
        initUI()
    }

    private fun initBinding() {
    }

    private fun initUI() {
//        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
//        binding.toolbar.setNavigationOnClickListener { finish() }
//        binding.toolbar.inflateMenu(R.menu.activity_join_menu_toolbar)
//        binding.toolbar.setOnMenuItemClickListener {
//            when(it.itemId) {
//                R.id.activity_join_menu_toolbar_action_close -> {
//                    val intent = Intent(this@JoinActivity, LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    startActivity(intent)
//                    true
//                }
//            }
//            false
//        }
//
//        binding.buttonJoin.setOnClickListener {
//            val intent = Intent(this@JoinActivity, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(intent)
//        }
    }
}