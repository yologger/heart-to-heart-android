package com.yologger.presentation.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.orhanobut.logger.Logger
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.main.MainActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.liveState.observe(this) {
            when(it) {
                is SplashViewModel.State.SUCCESS -> {
                    when(it.result) {
                        SplashViewModel.RESULT.NOT_LOGGED_IN -> navigateToLoginScreen()
                        SplashViewModel.RESULT.LOGGED_IN -> navigateToMainScreen()
                    }
                }
                is SplashViewModel.State.FAILURE -> {
                    when(it.error) {
                        SplashViewModel.ERROR.CLIENT_ERROR -> {
                            showToast("Client Error")
                            finish()
                        }
                        SplashViewModel.ERROR.NETWORK_ERROR -> {
                            showToast("Network Error")
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}