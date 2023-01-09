package com.deathhit.feature.thumbnail.thumbnail_list

import androidx.recyclerview.widget.RecyclerView
import com.deathhit.feature.thumbnail.databinding.ItemThumbnailListThumbnailBinding
import com.deathhit.feature.thumbnail.model.ThumbnailVO

class ThumbnailViewHolder(
    val binding: ItemThumbnailListThumbnailBinding,
    var item: ThumbnailVO? = null
) : RecyclerView.ViewHolder(binding.root)