package com.example.wikiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wikiapp.data.WikiRepository
import com.example.wikiapp.db.entities.CategoryEntity
import com.example.wikiapp.db.entities.FeaturedImageEntity
import com.example.wikiapp.db.entities.RandomArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WikiRepository
) : ViewModel() {

    val featuredImages: Flow<PagingData<FeaturedImageEntity>> =
        repository.getFeaturedImages().cachedIn(viewModelScope)
    val categories: Flow<PagingData<CategoryEntity>> =
        repository.getCategories().cachedIn(viewModelScope)
    val randomArticles: Flow<PagingData<RandomArticleEntity>> =
        repository.getRandomArticles().cachedIn(viewModelScope)
}