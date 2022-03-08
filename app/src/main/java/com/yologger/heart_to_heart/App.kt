package com.yologger.heart_to_heart

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.yologger.common.ThemeManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initTheme()
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            // .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            // .methodCount(0) // (Optional) How many method line to show. Default 2
            // .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            // .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
            // .tag("PRETTY_LOGGER") // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initTheme() {
        when (ThemeManager.getCurrentTheme(context = applicationContext)) {
            ThemeManager.ThemeMode.DARK -> { ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.DARK) }
            ThemeManager.ThemeMode.LIGHT -> { ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.LIGHT) }
            ThemeManager.ThemeMode.DEFAULT -> { ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.DEFAULT) }
        }
    }
}