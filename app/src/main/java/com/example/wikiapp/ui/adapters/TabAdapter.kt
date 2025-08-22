package com.example.wikiapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wikiapp.ui.home.CategoriesTabFragment
import com.example.wikiapp.ui.home.FeaturedImagesTabFragment
import com.example.wikiapp.ui.home.RandomArticlesTabFragment
import com.example.wikiapp.utils.Constants.TOTAL_TABS

class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TOTAL_TABS

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RandomArticlesTabFragment()
        1 -> FeaturedImagesTabFragment()
        2 -> CategoriesTabFragment()
        else -> Fragment()
    }
}