package com.example.wikiapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wikiapp.R
import com.example.wikiapp.db.entities.CategoryEntity

class CategoryAdapter : PagingDataAdapter<CategoryEntity, CategoryViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(
                oldItem: CategoryEntity, newItem: CategoryEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CategoryEntity, newItem: CategoryEntity
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)

    }
}

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val categoryText: TextView = view.findViewById(R.id.category_text)
    fun bind(item: CategoryEntity) {
        if (item.category.isNotEmpty()) categoryText.text = item.category
    }

}