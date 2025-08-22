package com.example.wikiapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_articles_remoteKey")
data class RandomArticleRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false) val randomArticleId: String,
    val imContinue: String? = null,
    val grnContinue: String? = null,
    val continueToken: String? = null
)