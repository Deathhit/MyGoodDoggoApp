package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R

class BreedViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        LAYOUT, parent, false
    )
) {
    companion object {
        private const val ID_TEXT_BRED_FOR = R.id.textView_bredFor
        private const val ID_TEXT_BREED_GROUP = R.id.textView_breedGroup
        private const val ID_TEXT_BREED_NAME = R.id.textView_breedName
        private const val ID_TEXT_LIFESPAN = R.id.textView_lifespan
        private const val ID_TEXT_TEMPERAMENT = R.id.textView_temperament
        private const val LAYOUT = R.layout.item_thumbnail_info_breed
    }

    val textBredFor: TextView = itemView.findViewById(ID_TEXT_BRED_FOR)
    val textBreedGroup: TextView = itemView.findViewById(ID_TEXT_BREED_GROUP)
    val textBreedName: TextView = itemView.findViewById(ID_TEXT_BREED_NAME)
    val textLifeSpan: TextView = itemView.findViewById(ID_TEXT_LIFESPAN)
    val textTemperament: TextView = itemView.findViewById(ID_TEXT_TEMPERAMENT)
}