package com.yologger.presentation.screen.main.more.follow

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityFollowBinding
import com.yologger.presentation.screen.main.more.follow.follower.FollowerFragment
import com.yologger.presentation.screen.main.more.follow.following.FollowingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFollowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)
        initBinding()
        initUI()
    }

    override fun onStart() {
        super.onStart()
        val builder = AlertDialog.Builder(this@FollowActivity)
        val alertDialog = builder
            .setMessage("팔로우, 팔로잉 기능이 곧 추가됩니다.")
            .setPositiveButton("확인") { _, _ ->
            }
            .create()
        alertDialog.show()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow)
        binding.lifecycleOwner = this
        // binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private val tabTextList = arrayListOf("팔로잉", "팔로워")

    inner class ViewPagerAdapter constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = tabTextList.size
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> FollowingFragment.newInstance()
                else -> FollowerFragment.newInstance()
            }
        }
    }
}