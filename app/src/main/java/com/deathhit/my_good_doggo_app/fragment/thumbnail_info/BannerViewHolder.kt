package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.ItemThumbnailInfoBannerBinding

class BannerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    ItemThumbnailInfoBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {
    val binding = ItemThumbnailInfoBannerBinding.bind(itemView)
}