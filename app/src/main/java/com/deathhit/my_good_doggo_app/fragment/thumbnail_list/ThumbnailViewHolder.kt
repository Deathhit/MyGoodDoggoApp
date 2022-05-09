package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.ItemThumbnailListThumbnailBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO

class ThumbnailViewHolder(
    val binding: ItemThumbnailListThumbnailBinding,
    var item: ThumbnailVO? = null
) : RecyclerView.ViewHolder(binding.root)