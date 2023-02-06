package com.deathhit.feature.thumbnail.fragment.thumbnail_list

import androidx.recyclerview.widget.RecyclerView
import com.deathhit.feature.thumbnail.databinding.ItemThumbnailListThumbnailBinding
import com.deathhit.feature.thumbnail.model.Thumbnail

class ThumbnailViewHolder(
    val binding: ItemThumbnailListThumbnailBinding,
    var item: Thumbnail? = null
) : RecyclerView.ViewHolder(binding.root)