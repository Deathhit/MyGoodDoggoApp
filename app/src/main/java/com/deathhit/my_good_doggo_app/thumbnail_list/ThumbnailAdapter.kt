package com.deathhit.my_good_doggo_app.thumbnail_list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val holder = ThumbnailViewHolder(parent)
        holder.itemView.setOnClickListener { holder.item?.let { onClickItem(it) } }
        return holder
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.item = getItem(position)
        holder.item?.let { item ->
            bindImageThumbnail(holder, item)
            bindTextId(holder, item)
        }
    }

    override fun onViewRecycled(holder: ThumbnailViewHolder) {
        super.onViewRecycled(holder)
        try {
            Glide.with(holder.imageThumbnail).clear(holder.imageThumbnail)
        } catch (ignored: Exception) {
        }
    }

    private fun bindImageThumbnail(holder: ThumbnailViewHolder, item: ThumbnailVO) {
        Glide.with(holder.imageThumbnail).load(item.thumbnailUrl)
            .centerCrop().format(DecodeFormat.PREFER_RGB_565).into(holder.imageThumbnail)
    }

    private fun bindTextId(holder: ThumbnailViewHolder, item: ThumbnailVO) {
        holder.textId.text = item.thumbnailId
    }

    abstract fun onClickItem(thumbnailVO: ThumbnailVO)
}