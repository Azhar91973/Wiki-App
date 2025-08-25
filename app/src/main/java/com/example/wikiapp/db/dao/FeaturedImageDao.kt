package com.example.wikiapp.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wikiapp.db.entities.FeaturedImageEntity

@Dao
interface FeaturedImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<FeaturedImageEntity>)

    @Query("SELECT * FROM featured_images")
    fun pagingSource(): PagingSource<Int, FeaturedImageEntity>

    @Query("DELETE FROM sqlite_sequence WHERE name = 'featured_images'")
    suspend fun deletePrimaryKeyIndex()

    @Query("DELETE FROM featured_images")
    suspend fun clearAll()
}