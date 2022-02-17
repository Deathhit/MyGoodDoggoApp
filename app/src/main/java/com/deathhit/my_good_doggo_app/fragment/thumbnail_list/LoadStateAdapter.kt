package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

abstract class LoadStateAdapter :
    LoadStateAdapter<LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val holder = LoadStateViewHolder(parent)
        configureBtnRetry(holder.binding.buttonRetry)
        return holder
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        bindBtnRetry(loadState, holder.binding.buttonRetry)
        bindProgressBar(loadState, holder.binding.progressBar)
        bindTextErrorMsg(loadState, holder.binding.textViewErrorMsg)
    }

    private fun bindBtnRetry(loadState: LoadState, btnRetry: Button) {
        btnRetry.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    private fun bindProgressBar(loadState: LoadState, progressBar: ProgressBar) {
        progressBar.visibility = toVisibility(loadState is LoadState.Loading)
    }

    private fun bindTextErrorMsg(loadState: LoadState, textErrorMsg: TextView) {
        if (loadState is LoadState.Error)
            textErrorMsg.text = loadState.error.localizedMessage
        textErrorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    private fun configureBtnRetry(btnRetry: Button) {
        btnRetry.setOnClickListener { onRetryLoading() }
    }

    private fun toVisibility(constraint: Boolean): Int = if (constraint)
        View.VISIBLE
    else
        View.GONE

    abstract fun onRetryLoading()
}