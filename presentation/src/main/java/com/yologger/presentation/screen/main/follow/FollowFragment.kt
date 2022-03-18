package com.yologger.presentation.screen.main.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentFollowBinding
import com.yologger.presentation.screen.main.follow.follower.FollowerFragment
import com.yologger.presentation.screen.main.follow.following.FollowingFragment

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val tabTextList = arrayListOf("FOLLOWING", "FOLLOWER")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    inner class ViewPagerAdapter constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> FollowingFragment.newInstance()
                else -> FollowerFragment.newInstance()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FollowFragment().apply {}
    }
}