package com.yologger.presentation.screen.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityMainBinding
import com.yologger.presentation.screen.main.home.HomeFragment
import com.yologger.presentation.screen.main.more.MoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener, NavigationBarView.OnItemReselectedListener  {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment: HomeFragment by lazy { HomeFragment.newInstance() }
    private val moreFragment: MoreFragment by lazy { MoreFragment.newInstance() }

    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        // binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.bottomNavigationView.setOnItemSelectedListener(this)
        binding.bottomNavigationView.setOnItemReselectedListener(this)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.activity_main_frameLayout, homeFragment).show(homeFragment)
            add(R.id.activity_main_frameLayout, moreFragment).hide(moreFragment)
        }.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_activity_main_bottom_navigation_view_action_home -> {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
                return true
            }
            R.id.menu_activity_main_bottom_navigation_view_action_more -> {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(moreFragment).commit()
                activeFragment = moreFragment
                return true
            }
        }
        return false
    }

    override fun onNavigationItemReselected(item: MenuItem) { 
        when(item.itemId) {
            R.id.menu_activity_main_bottom_navigation_view_action_home -> homeFragment.moveToTop()
            R.id.menu_activity_main_bottom_navigation_view_action_more -> { }
        }
    }
}