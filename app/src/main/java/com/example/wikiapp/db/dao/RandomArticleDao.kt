package com.example.wikiapp.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wikiapp.db.entities.RandomArticleEntity

@Dao
interface RandomArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<RandomArticleEntity>)

    @Query("SELECT * FROM random_articles")
    fun pagingSource(): PagingSource<Int, RandomArticleEntity>

    @Query("DELETE FROM sqlite_sequence WHERE name = 'random_articles'")
    suspend fun deletePrimaryKeyIndex()

    @Query("DELETE FROM random_articles")
    suspend fun clearAll()
}