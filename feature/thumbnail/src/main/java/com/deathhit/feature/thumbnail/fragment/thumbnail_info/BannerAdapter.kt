package com.deathhit.feature.thumbnail.fragment.thumbnail_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.feature.thumbnail.databinding.ItemThumbnailInfoBannerBinding
import com.deathhit.feature.thumbnail.model.ThumbnailVO

abstract class BannerAdapter : RecyclerView.Adapter<BannerViewHolder>() {
    companion object {
        private const val ITEM_COUNT = 1
        private const val ITEM_POS = 0
    }

    private var item: ThumbnailVO? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
        BannerViewHolder(
            ItemThumbnailInfoBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            binding.imageViewBanner.setOnClickListener { item?.let { onBannerClick(it) } }
        }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        item?.let { item ->
            with(holder.binding.imageViewBanner) {
                Glide.with(this).load(item.thumbnailUrl)
                    .fitCenter().format(DecodeFormat.PREFER_RGB_565).into(this)
            }
        }
    }

    override fun getItemCount(): Int = ITEM_COUNT

    fun notifyOnItemChanged(item: ThumbnailVO?) {
        this.item = item
        notifyItemChanged(ITEM_POS)
    }

    abstract fun onBannerClick(item: ThumbnailVO)
}