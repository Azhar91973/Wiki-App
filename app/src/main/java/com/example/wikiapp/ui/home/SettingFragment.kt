package com.example.wikiapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wikiapp.data.SharedPrefs
import com.example.wikiapp.databinding.FragmentSettingBinding
import com.example.wikiapp.ui.common.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {

    @Inject
    lateinit var prefs: SharedPrefs
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val appTheme get() = prefs.getThemePreference()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

        binding.switchTheme.setOnCheckedChangeListener { _, checkedId ->
            val themeMode = when (checkedId) {
                false -> SharedPrefs.Companion.THEME_LIGHT
                true -> SharedPrefs.Companion.THEME_DARK
            }
            // Save theme preference and apply it
            prefs.saveThemePreference(themeMode)
            ThemeManager.applyTheme(themeMode, requireActivity())
        }

    }

    fun setUpViews() {
        if (appTheme == SharedPrefs.Companion.THEME_LIGHT) binding.switchTheme.isChecked = false
        else if (appTheme == SharedPrefs.Companion.THEME_DARK) binding.switchTheme.isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}