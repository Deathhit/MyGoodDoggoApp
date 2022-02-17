package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.ItemThumbnailInfoBreedBinding

class BreedViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    ItemThumbnailInfoBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {
    val binding = ItemThumbnailInfoBreedBinding.bind(itemView)
}