package com.example.wikiapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikiapp.databinding.FragmentTabBinding
import com.example.wikiapp.ui.adapters.CategoryAdapter
import com.example.wikiapp.ui.adapters.LoadStateAdapter
import com.example.wikiapp.ui.common.BaseFragment
import com.example.wikiapp.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoriesTabFragment : BaseFragment<FragmentTabBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var rvAdapter: CategoryAdapter


    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentTabBinding {
        return FragmentTabBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObservers()
        setUpClickListeners()
    }

    override fun setUpViews() {
        rvAdapter = CategoryAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter.withLoadStateFooter(LoadStateAdapter {
                rvAdapter.retry()
            })
        }
    }

    override fun setUpClickListeners() {}

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collectLatest { pagingData ->
                    rvAdapter.submitData(lifecycle, pagingData)
                }
            }
        }

    }
}