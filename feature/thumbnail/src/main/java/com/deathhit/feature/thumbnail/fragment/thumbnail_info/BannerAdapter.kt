package com.deathhit.feature.thumbnail.fragment.thumbnail_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.feature.thumbnail.databinding.ItemThumbnailInfoBannerBinding

abstract class BannerAdapter : RecyclerView.Adapter<BannerViewHolder>() {
    companion object {
        private const val ITEM_COUNT = 1
        private const val ITEM_POS = 0
    }

    private var bannerUrl: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
        BannerViewHolder(
            ItemThumbnailInfoBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            binding.imageViewBanner.setOnClickListener { bannerUrl?.let { onBannerClick(it) } }
        }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        with(holder.binding.imageViewBanner) {
            if (bannerUrl != null)
                Glide.with(this).load(bannerUrl)
                    .fitCenter().format(DecodeFormat.PREFER_RGB_565).into(this)
        }
    }

    override fun getItemCount(): Int = ITEM_COUNT

    fun notifyBannerUrlChanged(bannerUrl: String?) {
        this.bannerUrl = bannerUrl
        notifyItemChanged(ITEM_POS)
    }

    abstract fun onBannerClick(imageUrl: String)
}