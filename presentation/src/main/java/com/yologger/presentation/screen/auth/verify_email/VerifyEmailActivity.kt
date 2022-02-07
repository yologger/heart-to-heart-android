package com.yologger.presentation.screen.auth.verify_email

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityVerifyEmailBinding
import com.yologger.presentation.screen.auth.join.JoinActivity

class VerifyEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.activity_verify_email_menu_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.activity_verify_email_menu_toolbar_action_close -> {
                    finish()
                    true
                }
            }
            false
        }

        binding.buttonNext.setOnClickListener {
            val intent = Intent(this@VerifyEmailActivity, JoinActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
   }
}