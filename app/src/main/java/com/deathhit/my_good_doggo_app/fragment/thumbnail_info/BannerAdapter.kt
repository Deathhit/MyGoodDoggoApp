package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.my_good_doggo_app.model.ThumbnailVO

class BannerAdapter : RecyclerView.Adapter<BannerViewHolder>() {
    companion object {
        private const val ITEM_COUNT = 1
        private const val ITEM_POS = 0
    }

    private var item: ThumbnailVO? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
        BannerViewHolder(parent)

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        item?.let { item ->
            bindImageBanner(item, holder.binding.imageViewBanner)
        }
    }

    override fun getItemCount(): Int = ITEM_COUNT

    fun notifyOnItemChanged(item: ThumbnailVO?) {
        this.item = item
        notifyItemChanged(ITEM_POS)
    }

    private fun bindImageBanner(item: ThumbnailVO, imageBanner: ImageView) {
        Glide.with(imageBanner).load(item.thumbnailUrl)
            .fitCenter().format(DecodeFormat.PREFER_RGB_565).into(imageBanner)
    }
}