package com.example.wikiapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "featured_images")
data class FeaturedImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pageId: Int,
    val title: String,
    val user: String,
    val timestamp: String,
    val url: String
)