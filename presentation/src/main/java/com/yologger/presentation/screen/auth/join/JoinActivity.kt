package com.yologger.presentation.screen.auth.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityJoinBinding
import com.yologger.presentation.screen.auth.login.LoginActivity

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.activity_join_menu_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.activity_join_menu_toolbar_action_close -> {
                    val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
            }
            false
        }

        binding.buttonJoin.setOnClickListener {
            val intent = Intent(this@JoinActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}