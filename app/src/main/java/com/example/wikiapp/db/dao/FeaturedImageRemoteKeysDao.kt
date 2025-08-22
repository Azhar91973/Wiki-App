package com.example.wikiapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wikiapp.db.entities.FeaturedImageRemoteKeys

@Dao
interface FeaturedImageRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: FeaturedImageRemoteKeys)

    @Query("SELECT * FROM featured_image_remote_keys WHERE featuredImageId = :featuredImageId")
    suspend fun getLastKey(featuredImageId: String): FeaturedImageRemoteKeys?

    @Query("DELETE FROM featured_image_remote_keys")
    suspend fun clearRemoteKeys()
}