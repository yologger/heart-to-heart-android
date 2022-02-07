package com.yologger.presentation.screen.main.settings.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yologger.common.ThemeManager
import com.yologger.presentation.R

class ThemeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ThemeRVAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        initRecyclerView()
        initToolbar()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.activity_theme_rv)
        recyclerViewAdapter = ThemeRVAdapter(this) { position ->
            when(position) {
                0 -> ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.LIGHT)
                1 -> ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.DARK)
            }
        }
        recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.activity_theme_tb)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        toolbar.setNavigationOnClickListener { finish() }
    }
}