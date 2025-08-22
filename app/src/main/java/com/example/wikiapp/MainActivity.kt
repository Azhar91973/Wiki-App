package com.example.wikiapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wikiapp.data.SharedPrefs
import com.example.wikiapp.databinding.ActivityMainBinding
import com.example.wikiapp.ui.common.ThemeManager
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout

    @Inject
    lateinit var prefs: SharedPrefs
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applySavedTheme()
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

    }

    private fun applySavedTheme() {
        val savedTheme = prefs.getThemePreference()
        val themeToApply = if (savedTheme == SharedPrefs.THEME_SYSTEM_DEFAULT) {
            if (ThemeManager.isSystemDarkTheme(this)) SharedPrefs.THEME_DARK else SharedPrefs.THEME_LIGHT
        } else savedTheme

        prefs.saveThemePreference(themeToApply)
        ThemeManager.applyTheme(themeToApply, this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}