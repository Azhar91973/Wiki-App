package com.example.wikiapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "featured_image_remote_keys")
data class FeaturedImageRemoteKeys(
    @PrimaryKey(autoGenerate = false) val featuredImageId: String, val nextPage: String? = null
)