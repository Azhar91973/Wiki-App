package com.example.wikiapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wikiapp.R
import com.example.wikiapp.db.entities.FeaturedImageEntity

class FeaturedImageAdapter : PagingDataAdapter<FeaturedImageEntity, FeaturedImageViewHolder>(
    DIFF_CALLBACK
) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeaturedImageEntity>() {
            override fun areItemsTheSame(
                oldItem: FeaturedImageEntity, newItem: FeaturedImageEntity
            ): Boolean = oldItem.pageId == newItem.pageId

            override fun areContentsTheSame(
                oldItem: FeaturedImageEntity, newItem: FeaturedImageEntity
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FeaturedImageViewHolder {
        return FeaturedImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FeaturedImageViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }
}

class FeaturedImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.featured_image)
    val titleText: TextView = view.findViewById(R.id.image_title)
    val timeStamp: TextView = view.findViewById(R.id.image_timestamp)
    val userText: TextView = view.findViewById(R.id.image_user)

    fun bind(item: FeaturedImageEntity) {
        Glide.with(imageView).load(item.url).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
        if (item.title.isNotEmpty()) titleText.text = item.title
        if (item.timestamp.isNotEmpty()) timeStamp.text = item.timestamp
        if (item.user.isNotEmpty()) userText.text = item.user
    }
}