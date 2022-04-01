package com.yologger.presentation.screen.auth.join

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yologger.presentation.R
import com.yologger.presentation.component.LoadingDialog
import com.yologger.presentation.databinding.ActivityJoinBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinActivity : AppCompatActivity() {

    private val viewModel: JoinViewModel by viewModels()
    private lateinit var binding: ActivityJoinBinding
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.menu_activity_join_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_activity_join_toolbar_action_close -> {
                    val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
            }
            false
        }
    }

    private fun observeViewModel() {
        val email = intent.getStringExtra("email")
        viewModel.liveEmail.value = email

        viewModel.liveIsLoading.observe(this) {
            if (it) {
                loadingDialog = LoadingDialog(this)
                loadingDialog?.show("Loading")
            } else {
                loadingDialog?.dismiss()
                loadingDialog = null
            }
        }

        viewModel.liveState.observe(this) {
            when (it) {
                is JoinViewModel.State.SUCCESS -> {
                    showToast("회원가입되었습니다.")
                    val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                is JoinViewModel.State.FAILURE -> {
                    when(it.error) {
                        JoinViewModel.Error.CLIENT_ERROR -> showToast("Client Error")
                        JoinViewModel.Error.NETWORK_ERROR -> showToast("Network Error")
                        JoinViewModel.Error.INVALID_PARAMS -> showToast("Invalid Parameters")
                        JoinViewModel.Error.MEMBER_ALREADY_EXIST -> showToast("중복된 이메일입니다.")
                        JoinViewModel.Error.JSON_PARSE_ERROR -> showToast("Json Parsing Error")
                    }
                }
            }
        }
    }
}