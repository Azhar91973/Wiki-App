package com.example.wikiapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wikiapp.db.entities.RandomArticleRemoteKeyEntity

@Dao
interface RandomArticleRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RandomArticleRemoteKeyEntity)

    @Query("SELECT * FROM random_articles_remoteKey WHERE randomArticleId = :randomArticleId")
    suspend fun getLastKey(randomArticleId: String): RandomArticleRemoteKeyEntity?


    @Query("DELETE FROM random_articles_remoteKey")
    suspend fun clearRemoteKeys()

}