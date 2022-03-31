package com.yologger.presentation.screen.main.more.settings.handle_blocked_users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityHandleBlockedUsersBinding

class HandleBlockedUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHandleBlockedUsersBinding
    private lateinit var recyclerViewAdapter: BlockedUsersRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_blocked_users)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_handle_blocked_users)
        binding.lifecycleOwner = this

    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }

        recyclerViewAdapter = BlockedUsersRVAdapter(this@HandleBlockedUsersActivity)
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this@HandleBlockedUsersActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }
}