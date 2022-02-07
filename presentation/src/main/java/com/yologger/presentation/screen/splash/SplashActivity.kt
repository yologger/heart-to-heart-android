package com.yologger.presentation.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.main.MainActivity

class SplashActivity : AppCompatActivity() {

    val isLoggedIn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLoggedIn) {
            navigateToMainScreen()
        } else {
            navigateToLoginScreen()
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