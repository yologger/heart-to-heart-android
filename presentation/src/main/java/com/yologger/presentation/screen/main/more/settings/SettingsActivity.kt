package com.yologger.presentation.screen.main.more.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivitySettingsBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var recyclerViewAdapter: SettingsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this
        // binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_24_white)
        binding.toolbar.setNavigationOnClickListener { finish() }
        recyclerViewAdapter = SettingsRVAdapter { position ->
            when(position) {
                1 -> {
                    val builder = AlertDialog.Builder(this@SettingsActivity)
                    val alertDialog = builder
                        .setMessage("로그아웃 하시겠어요?")
                        .setPositiveButton("로그아웃") { _, _ -> viewModel.logout() }
                        .setNegativeButton("취소") { _, _ -> }
                        .create()
                    alertDialog.show()
                }
            }
        }
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(this) {
            when (it) {
                is SettingsViewModel.State.SUCCESS -> {
                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                is SettingsViewModel.State.FAILURE -> {
                    when(it.error) {
                        SettingsViewModel.Error.NETWORK_ERROR -> showToast("Network Error")
                        SettingsViewModel.Error.CLIENT_ERROR -> showToast("Client Error")
                    }
                }
            }
        }
    }
}