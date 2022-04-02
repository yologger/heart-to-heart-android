package com.yologger.presentation.screen.main.more.settings.handleBlockedUsers

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityHandleBlockedUsersBinding
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HandleBlockedUsersActivity : AppCompatActivity() {

    private val viewModel: HandleBlockedUsersViewModel by viewModels()
    private lateinit var binding: ActivityHandleBlockedUsersBinding
    private lateinit var recyclerViewAdapter: BlockedUsersRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_blocked_users)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_handle_blocked_users)
        binding.lifecycleOwner = this
    }

    private val onUnblocked = { memberId: Long ->
        viewModel.unblock(memberId)
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_black_24_filled_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }

        recyclerViewAdapter = BlockedUsersRVAdapter(context = this@HandleBlockedUsersActivity, onUnblocked = onUnblocked)
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this@HandleBlockedUsersActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(this) {
            when(it) {
                is HandleBlockedUsersViewModel.State.GetBlockingMembersFailure -> {

                }
                is HandleBlockedUsersViewModel.State.UnblockMemberFailure -> {

                }
                is HandleBlockedUsersViewModel.State.UnblockMemberSuccess -> showToast("차단이 해제되었습니다.")
            }
        }

        viewModel.liveMembers.observe(this) {
            recyclerViewAdapter.updateMembers(it)
        }
    }
}