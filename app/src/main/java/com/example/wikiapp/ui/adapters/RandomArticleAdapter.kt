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
import com.example.wikiapp.R
import com.example.wikiapp.db.entities.RandomArticleEntity

class RandomArticleAdapter :
    PagingDataAdapter<RandomArticleEntity, ArticleViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RandomArticleEntity>() {
            override fun areItemsTheSame(
                oldItem: RandomArticleEntity, newItem: RandomArticleEntity
            ): Boolean = oldItem.pageId == newItem.pageId

            override fun areContentsTheSame(
                oldItem: RandomArticleEntity, newItem: RandomArticleEntity
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }
}


class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val articleTitle: TextView = view.findViewById(R.id.article_title)
    val articleContent: TextView = view.findViewById(R.id.article_content)
    val articleId: TextView = view.findViewById(R.id.article_id)
    val articleImage: ImageView = view.findViewById(R.id.article_image)

    fun bind(article: RandomArticleEntity) {
        if (article.title.isNotEmpty()) articleTitle.text = article.title
        if (!article.content.isNullOrEmpty()) articleContent.text = article.content
        articleId.text = article.pageId.toString()
        Glide.with(itemView.context).load(article.imageUrl).into(articleImage)

    }
}