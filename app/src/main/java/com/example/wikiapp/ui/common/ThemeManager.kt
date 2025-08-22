package com.example.wikiapp.ui.common

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsControllerCompat
import com.example.wikiapp.data.SharedPrefs

object ThemeManager {
    fun applyTheme(themeMode: Int, activity: Activity) {
        val window = activity.window
        val controller = WindowInsetsControllerCompat(window, window.decorView)

        when (themeMode) {
            SharedPrefs.Companion.THEME_LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                controller.isAppearanceLightStatusBars = true // Light mode → Dark icons
            }

            SharedPrefs.Companion.THEME_DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                controller.isAppearanceLightStatusBars = false // Dark mode → Light icons
            }

            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                controller.isAppearanceLightStatusBars = isSystemDarkTheme(activity).not()
            }
        }
    }

    fun isSystemDarkTheme(activity: Activity): Boolean {
        val nightModeFlags =
            activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }
}