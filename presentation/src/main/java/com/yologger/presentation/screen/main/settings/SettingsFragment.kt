package com.yologger.presentation.screen.main.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yologger.presentation.R
import com.yologger.presentation.screen.main.settings.theme.ThemeActivity

class SettingsFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: SettingsRVAdapter

    private val onItemClick = { position: Int ->
        when(position) {
            0 -> startActivity(Intent(requireActivity(), ThemeActivity::class.java))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        toolbar = rootView.findViewById(R.id.fragment_settings_toolbar)
        recyclerView = rootView.findViewById(R.id.fragment_settings_recyclerView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = SettingsRVAdapter(onItemClick)
        recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment().apply {}
    }
}