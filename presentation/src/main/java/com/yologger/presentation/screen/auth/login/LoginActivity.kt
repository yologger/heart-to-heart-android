package com.yologger.presentation.screen.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yologger.presentation.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.buttonLogin.setOnClickListener {
        }
    }
}