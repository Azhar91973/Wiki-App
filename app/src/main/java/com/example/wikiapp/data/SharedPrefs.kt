package com.example.wikiapp.data


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefs(context: Context) {
    companion object {
        private const val KEY_THEME_MODE = "theme_mode" // Key for theme preference
        const val THEME_SYSTEM_DEFAULT = -1
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        private const val SHARED_PREFERENCE = "MySharedPrefs"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

    // Saves the theme mode.
    fun saveThemePreference(themeMode: Int) {
        sharedPreferences.edit {
            putInt(
                KEY_THEME_MODE, themeMode
            )
        }
    }

    // Retrieves the theme mode.
    fun getThemePreference(): Int {
        return sharedPreferences.getInt(
            KEY_THEME_MODE, THEME_SYSTEM_DEFAULT
        )
    }
}