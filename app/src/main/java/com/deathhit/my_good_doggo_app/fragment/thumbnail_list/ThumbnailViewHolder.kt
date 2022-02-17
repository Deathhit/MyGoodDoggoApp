package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.ItemThumbnailListThumbnailBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO

class ThumbnailViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        ItemThumbnailListThumbnailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).root
    ) {
    val binding = ItemThumbnailListThumbnailBinding.bind(itemView)

    var item: ThumbnailVO? = null
}