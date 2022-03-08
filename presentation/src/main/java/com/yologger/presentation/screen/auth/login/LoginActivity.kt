package com.yologger.presentation.screen.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orhanobut.logger.Logger
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityLoginBinding
import com.yologger.presentation.screen.auth.verify_email.VerifyEmailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {

    }
    
    fun onJoinButtonClicked(view: View) {
        val intent = Intent(this, VerifyEmailActivity::class.java)
        startActivity(intent)
    }
}