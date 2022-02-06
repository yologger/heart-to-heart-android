package com.yologger.presentation.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yologger.presentation.screen.auth.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    val isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLoggedIn) {
            navigateToMainScreen()
        } else {
            navigateToLoginScreen()
        }
    }

    private fun navigateToMainScreen() {

    }

    private fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}