package com.yologger.presentation.screen.main.more.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivitySettingsItemBinding

class SettingsRVAdapter constructor(
    val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<SettingsRVAdapter.SettingsItem>() {
    val menu = listOf(
        "테마",
        "버전 정보",
        "로그아웃"
    )

    inner class SettingsItem(val binding: ActivitySettingsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.textViewName.text = menu[position]
            binding.rootView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) onItemClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsItem {
        val binding: ActivitySettingsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.activity_settings_item, parent, false)
        return SettingsItem(binding)
    }

    override fun onBindViewHolder(holder: SettingsItem, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menu.size
}