package com.yologger.presentation.screen.auth.agreePolicy

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityAgreePolicyBinding
import com.yologger.presentation.screen.auth.agreePolicy.showPolicy.ShowInformationPolicyActivity
import com.yologger.presentation.screen.auth.agreePolicy.showTerms.ShowTermsActivity
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.auth.verifyEmail.VerifyEmailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreePolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgreePolicyBinding
    private val viewModel: AgreePolicyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agree_policy)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agree_policy)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_black_24_outlined_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.menu_activity_agree_policy_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_activity_agree_policy_toolbar_action_close -> {
                    val intent = Intent(this@AgreePolicyActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
            }
            false
        }

        binding.buttonTerms.setOnClickListener {
            val intent = Intent(this@AgreePolicyActivity, ShowTermsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonPolicy.setOnClickListener {
            val intent = Intent(this@AgreePolicyActivity, ShowInformationPolicyActivity::class.java)
            startActivity(intent)
        }

        binding.buttonNext.setOnClickListener {
            val intent = Intent(this@AgreePolicyActivity, VerifyEmailActivity::class.java)
            startActivity(intent)
        }
    }
}