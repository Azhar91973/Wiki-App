package com.example.wikiapp.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wikiapp.db.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)
    @Query("SELECT * FROM categories")
    fun pagingSource(): PagingSource<Int, CategoryEntity>

    @Query("DELETE FROM categories")
    suspend fun clearAll()
}