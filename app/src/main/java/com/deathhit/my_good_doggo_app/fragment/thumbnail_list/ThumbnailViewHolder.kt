package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.model.ThumbnailVO

class ThumbnailViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(LAYOUT, parent, false)
    ) {
    companion object {
        private const val ID_IMAGE_THUMBNAIL = R.id.imageView_thumbnail
        private const val ID_TEXT_ID = R.id.textView_id
        private const val LAYOUT = R.layout.item_thumbnail_list_thumbnail
    }

    val imageThumbnail: ImageView = itemView.findViewById(ID_IMAGE_THUMBNAIL)
    val textId: TextView = itemView.findViewById(ID_TEXT_ID)

    var item: ThumbnailVO? = null
}