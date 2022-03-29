package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.my_good_doggo_app.model.ThumbnailVO

abstract class BannerAdapter : RecyclerView.Adapter<BannerViewHolder>() {
    companion object {
        private const val ITEM_COUNT = 1
        private const val ITEM_POS = 0
    }

    private var item: ThumbnailVO? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
        BannerViewHolder(parent).apply {
            binding.run {
                imageViewBanner.setOnClickListener { onBannerClick(item) }
            }
        }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        item?.let { item ->
            holder.binding.run {
                imageViewBanner.run {
                    Glide.with(this).load(item.thumbnailUrl)
                        .fitCenter().format(DecodeFormat.PREFER_RGB_565).into(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = ITEM_COUNT

    fun notifyOnItemChanged(item: ThumbnailVO?) {
        this.item = item
        notifyItemChanged(ITEM_POS)
    }

    abstract fun onBannerClick(item: ThumbnailVO?)
}