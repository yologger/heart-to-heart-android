package com.yologger.presentation.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityMainBinding
import com.yologger.presentation.screen.main.follow.FollowFragment
import com.yologger.presentation.screen.main.home.HomeFragment
import com.yologger.presentation.screen.main.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBinding()
        initBottomNavigationView()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initBottomNavigationView() {
        supportFragmentManager.beginTransaction().add(R.id.activity_main_frameLayout, HomeFragment.newInstance()).commit()
        binding.activityMainBottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.activity_main_menu_bottom_navigation_view_item_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.activity_main_frameLayout, HomeFragment.newInstance()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.activity_main_menu_bottom_navigation_view_item_follow -> {
                    supportFragmentManager.beginTransaction().replace(R.id.activity_main_frameLayout, FollowFragment.newInstance()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.activity_main_menu_bottom_navigation_view_item_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.activity_main_frameLayout, SettingsFragment.newInstance()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }
}