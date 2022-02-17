package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.ItemThumbnailListLoadStateBinding

class LoadStateViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        ItemThumbnailListLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).root
    ) {
    val binding = ItemThumbnailListLoadStateBinding.bind(itemView)
}