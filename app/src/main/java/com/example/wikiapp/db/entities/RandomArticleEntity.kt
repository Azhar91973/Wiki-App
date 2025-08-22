package com.example.wikiapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_articles")
data class RandomArticleEntity(
    @PrimaryKey(autoGenerate = false) val pageId: Int,
    val title: String,
    val content: String?,
    val imageUrl: String?
)