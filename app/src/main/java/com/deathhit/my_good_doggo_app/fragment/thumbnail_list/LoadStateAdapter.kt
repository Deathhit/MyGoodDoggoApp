package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

abstract class LoadStateAdapter :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val holder = LoadStateViewHolder(parent)
        holder.btnRetry.setOnClickListener { onRetryLoading() }
        return holder
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error)
            holder.textErrorMsg.text = loadState.error.localizedMessage
        holder.progressBar.visibility = toVisibility(loadState is LoadState.Loading)
        holder.btnRetry.visibility = toVisibility(loadState !is LoadState.Loading)
        holder.textErrorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    private fun toVisibility(constraint: Boolean): Int = if (constraint)
        View.VISIBLE
    else
        View.GONE

    abstract fun onRetryLoading()
}