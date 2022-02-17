package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val holder = ThumbnailViewHolder(parent)
        configureItemView(holder, holder.itemView)
        return holder
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.item = getItem(position)?.also { item ->
            bindImageThumbnail(item, holder.binding.imageViewThumbnail)
            bindTextId(item, holder.binding.textViewId)
        }
    }

    override fun onViewRecycled(holder: ThumbnailViewHolder) {
        super.onViewRecycled(holder)
        recycleImageThumbnail(holder.binding.imageViewThumbnail)
    }

    private fun bindImageThumbnail(item: ThumbnailVO, imageThumbnail: ImageView) {
        Glide.with(imageThumbnail).load(item.thumbnailUrl)
            .centerCrop().format(DecodeFormat.PREFER_RGB_565).into(imageThumbnail)
    }

    private fun bindTextId(item: ThumbnailVO, textId: TextView) {
        textId.text = item.thumbnailId
    }

    private fun configureItemView(holder: ThumbnailViewHolder, itemView: View) {
        itemView.setOnClickListener { holder.item?.let { onClickItem(it) } }
    }

    private fun recycleImageThumbnail(imageThumbnail: ImageView) {
        try {
            Glide.with(imageThumbnail).clear(imageThumbnail)
        } catch (ignored: Exception) {
        }
    }

    abstract fun onClickItem(thumbnailVO: ThumbnailVO)
}