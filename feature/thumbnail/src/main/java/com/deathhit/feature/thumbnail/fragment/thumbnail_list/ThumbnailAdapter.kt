package com.deathhit.feature.thumbnail.fragment.thumbnail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.feature.thumbnail.databinding.ItemThumbnailListThumbnailBinding
import com.deathhit.feature.thumbnail.model.ThumbnailVO

abstract class ThumbnailAdapter :
    PagingDataAdapter<ThumbnailVO, ThumbnailViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR =
            object : DiffUtil.ItemCallback<ThumbnailVO>() {
                override fun areItemsTheSame(oldItem: ThumbnailVO, newItem: ThumbnailVO): Boolean =
                    oldItem.thumbnailId == newItem.thumbnailId

                override fun areContentsTheSame(oldItem: ThumbnailVO, newItem: ThumbnailVO): Boolean =
                    oldItem == newItem
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder =
        ThumbnailViewHolder(
            ItemThumbnailListThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { item?.let { onClickItem(it) } }
        }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.item = getItem(position)?.also { item ->
            with(holder.binding.imageViewThumbnail) {
                Glide.with(this).load(item.thumbnailUrl)
                    .centerCrop().format(DecodeFormat.PREFER_RGB_565).into(this)
            }

            with(holder.binding.textViewId) {
                text = item.thumbnailId
            }
        }
    }

    override fun onViewRecycled(holder: ThumbnailViewHolder) {
        super.onViewRecycled(holder)
        with(holder.binding.imageViewThumbnail) {
            try {
                Glide.with(this).clear(this)
            } catch (ignored: Exception) {
            }
        }
    }

    abstract fun onClickItem(thumbnail: ThumbnailVO)
}