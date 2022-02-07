package com.yologger.presentation.screen.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yologger.presentation.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.yologger.presentation.screen.auth.find_password.FindPasswordActivity
import com.yologger.presentation.screen.auth.join.JoinActivity
import com.yologger.presentation.screen.auth.verify_email.VerifyEmailActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {  }
        binding.buttonForgotPassword.setOnClickListener { startActivity(Intent(this@LoginActivity, FindPasswordActivity::class.java)) }
        binding.buttonJoin.setOnClickListener { startActivity(Intent(this@LoginActivity, VerifyEmailActivity::class.java)) }
    }
}