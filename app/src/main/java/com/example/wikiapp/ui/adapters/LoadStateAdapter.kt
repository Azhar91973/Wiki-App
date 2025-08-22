package com.example.wikiapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wikiapp.R

class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(
        holder: LoadStateViewHolder, loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): LoadStateViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.load_state_fotter, parent, false)
        return LoadStateViewHolder(view, retry)
    }
}

class LoadStateViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {
    val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    val errorText: TextView = view.findViewById(R.id.error_text)
    val retryButton: Button = view.findViewById(R.id.retry_button)

    init {
        retryButton.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        errorText.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        retryButton.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
    }
}