package com.example.wikiapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wikiapp.db.entities.CategoryRemoteKeyEntity

@Dao
interface CategoryRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeys: CategoryRemoteKeyEntity)

    @Query("SELECT * FROM categories_remote_key WHERE category = :category")
    suspend fun remoteKeyByCategory(category: String): CategoryRemoteKeyEntity?

    @Query("DELETE FROM categories_remote_key")
    suspend fun clearAll()

}