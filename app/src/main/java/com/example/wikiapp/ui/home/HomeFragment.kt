package com.example.wikiapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.wikiapp.MainActivity
import com.example.wikiapp.R
import com.example.wikiapp.databinding.FragmentHomeBinding
import com.example.wikiapp.ui.adapters.TabAdapter
import com.example.wikiapp.ui.common.BaseFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var drawerLayout: DrawerLayout

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObservers()
        setUpClickListeners()
    }

    override fun setUpViews() {
        binding.viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Random Articles"
                1 -> "Featured Images"
                2 -> "Categories"
                else -> ""
            }
        }.attach()
        setupDrawerLayout()
    }

    private fun setupDrawerLayout() {
        drawerLayout = (requireActivity() as MainActivity).drawerLayout
        val navigationView: NavigationView = (requireActivity() as MainActivity).navigationView

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.drawer_setting -> {
                    findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
                    drawerLayout.close()
                }
            }
            true
        }
    }

    override fun setUpClickListeners() {}

    override fun setUpObservers() {}
}