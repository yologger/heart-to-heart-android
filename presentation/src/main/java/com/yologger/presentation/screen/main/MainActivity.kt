package com.yologger.presentation.screen.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.yologger.presentation.R
import com.yologger.presentation.screen.main.follow.FollowFragment
import com.yologger.presentation.screen.main.home.HomeFragment
import com.yologger.presentation.screen.main.more.MoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener, NavigationBarView.OnItemReselectedListener  {

    private val frameLayout: FrameLayout by lazy { findViewById(R.id.activity_main_frameLayout) }
    private val bottomNavigationView: BottomNavigationView by lazy { findViewById(R.id.activity_main_bottomNavigationView) }

    private val homeFragment: HomeFragment by lazy { HomeFragment.newInstance() }
    private val followFragment: FollowFragment by lazy { FollowFragment.newInstance() }
    private val moreFragment: MoreFragment by lazy { MoreFragment.newInstance() }

    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(this)
        bottomNavigationView.setOnItemReselectedListener(this)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.activity_main_frameLayout, homeFragment).show(homeFragment)
            add(R.id.activity_main_frameLayout, followFragment).hide(followFragment)
            add(R.id.activity_main_frameLayout, moreFragment).hide(moreFragment)
        }.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.activity_main_menu_bottom_navigation_view_item_home -> {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
                return true
            }
            R.id.activity_main_menu_bottom_navigation_view_item_follow -> {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(followFragment).commit()
                activeFragment = followFragment
                return true
            }
            R.id.activity_main_menu_bottom_navigation_view_item_settings -> {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(moreFragment).commit()
                activeFragment = moreFragment
                return true
            }
        }
        return false
    }

    override fun onNavigationItemReselected(item: MenuItem) { 
        when(item.itemId) {
            R.id.activity_main_menu_bottom_navigation_view_item_home -> homeFragment.moveToTop()
            R.id.activity_main_menu_bottom_navigation_view_item_follow -> {

            }
            R.id.activity_main_menu_bottom_navigation_view_item_settings -> {

            }
        }
    }
}