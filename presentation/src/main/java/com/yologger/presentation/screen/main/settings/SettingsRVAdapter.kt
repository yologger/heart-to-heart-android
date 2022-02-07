package com.yologger.presentation.screen.main.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yologger.presentation.R

class SettingsRVAdapter constructor(
    private val onItemClickListener: (Int) -> Unit
): RecyclerView.Adapter<SettingsRVAdapter.SettingsViewHolder>() {

    private val menus = listOf(
        "Theme"
    )

    inner class SettingsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.fragment_settings_item_name)
        private val textViewValue: TextView = itemView.findViewById(R.id.fragment_settings_item_value)

        fun bind(name: String = "", value: String = "") {
            textViewName.text = name
            textViewValue.text = value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_settings_item, parent, false)
        return SettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val menu = menus[position]
        holder.bind(name = menu)
        holder.itemView.setOnClickListener { onItemClickListener(position) }
    }

    override fun getItemCount(): Int = menus.size
}