package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import java.lang.Exception

abstract class ThumbnailAdapter :
    PagingDataAdapter<ThumbnailVO, ThumbnailViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ThumbnailVO>() {
            override fun areItemsTheSame(oldItem: ThumbnailVO, newItem: ThumbnailVO): Boolean =
                oldItem.thumbnailId == newItem.thumbnailId

            override fun areContentsTheSame(oldItem: ThumbnailVO, newItem: ThumbnailVO): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder =
        ThumbnailViewHolder(parent).apply {
            itemView.setOnClickListener { item?.let { onClickItem(it) } }
        }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.item = getItem(position)?.also { item ->
            holder.binding.run {
                imageViewThumbnail.run {
                    Glide.with(this).load(item.thumbnailUrl)
                        .centerCrop().format(DecodeFormat.PREFER_RGB_565).into(this)
                }

                textViewId.run {
                    text = item.thumbnailId
                }
            }
        }
    }

    override fun onViewRecycled(holder: ThumbnailViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.run {
            imageViewThumbnail.run {
                try {
                    Glide.with(this).clear(this)
                } catch (ignored: Exception) {
                }
            }
        }
    }

    abstract fun onClickItem(thumbnailVO: ThumbnailVO)
}