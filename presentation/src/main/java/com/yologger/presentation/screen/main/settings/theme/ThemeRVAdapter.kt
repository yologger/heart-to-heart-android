package com.yologger.presentation.screen.main.settings.theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yologger.common.ThemeManager
import com.yologger.presentation.R

class ThemeRVAdapter(
    private val context: Context,
    private val onItemSelect: (Int) -> Unit
): RecyclerView.Adapter<ThemeRVAdapter.ViewHolder>() {

    private val menus = listOf("Light", "Dark")
    private val checked =  Array<Boolean>(2){ false }

    init {
        when(ThemeManager.getCurrentTheme(context)) {
            ThemeManager.ThemeMode.LIGHT -> checked[0] = true
            ThemeManager.ThemeMode.DARK -> checked[1] = true
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.activity_theme_item_tv)
        private val checkBox: CheckBox = itemView.findViewById(R.id.activity_theme_item_cb)

        fun bind(menu: String, position: Int) {
            textViewName.text = menu
            checkBox.isChecked = checked[position]
            itemView.setOnClickListener {
                onItemSelect(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_theme_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menus[position], position)
    }

    override fun getItemCount(): Int = menus.size
}