package com.yologger.common

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    enum class ThemeMode (val value: Int) {
        DEFAULT(0),
        LIGHT(1),
        DARK(2)
    }

    fun applyTheme(context: Context, themeMode: ThemeMode) {

        var newTheme: ThemeMode
        val sharedPreferences = context.getSharedPreferences(Constant.KEY_PREFERENCE, Context.MODE_PRIVATE) ?: return

        when (themeMode) {
            ThemeMode.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                newTheme = ThemeMode.LIGHT
            }
            ThemeMode.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                newTheme = ThemeMode.DARK
            }
            else ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    newTheme = ThemeMode.DEFAULT
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                    newTheme = ThemeMode.DEFAULT
                }
        }

        with(sharedPreferences.edit()) {
            putInt(Constant.KEY_THEME, newTheme.value)
            commit()
        }
    }

    fun getCurrentTheme(context: Context): ThemeMode {
        val sharedPreferences = context.getSharedPreferences(Constant.KEY_PREFERENCE, Context.MODE_PRIVATE)
        val currentThemeValue = sharedPreferences.getInt(Constant.KEY_THEME, ThemeMode.DEFAULT.value)
        val currentTheme = when(currentThemeValue) {
            ThemeMode.LIGHT.value -> { ThemeMode.LIGHT }
            ThemeMode.DARK.value -> { ThemeMode.DARK }
            else -> { ThemeMode.DARK }
        }
        return currentTheme
    }
}