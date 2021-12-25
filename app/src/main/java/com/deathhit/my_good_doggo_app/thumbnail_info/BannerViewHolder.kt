package com.deathhit.my_good_doggo_app.thumbnail_info

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R

class BannerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        LAYOUT, parent, false
    )
) {
    companion object {
        private const val ID_IMAGE_BANNER = R.id.imageView_banner
        private const val LAYOUT = R.layout.item_thumbnail_info_banner
    }

    val imageBanner: ImageView = itemView.findViewById(ID_IMAGE_BANNER)
}