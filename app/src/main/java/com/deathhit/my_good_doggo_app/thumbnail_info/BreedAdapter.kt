package com.deathhit.my_good_doggo_app.thumbnail_info

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.deathhit.my_good_doggo_app.base.model.BreedVO

class BreedAdapter : ListAdapter<BreedVO, BreedViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<BreedVO>() {
            override fun areItemsTheSame(oldItem: BreedVO, newItem: BreedVO): Boolean =
                oldItem.breedId == newItem.breedId

            override fun areContentsTheSame(oldItem: BreedVO, newItem: BreedVO): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder =
        BreedViewHolder(parent)

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        getItem(position)?.let { item ->
            item.bredFor?.let {
                holder.textBredFor.visibility = View.VISIBLE
                holder.textBredFor.text = it
            } ?: kotlin.run { holder.textBredFor.visibility = View.GONE }
            item.breedGroup?.let {
                holder.textBreedGroup.visibility = View.VISIBLE
                holder.textBreedGroup.text = it
            } ?: kotlin.run { holder.textBreedGroup.visibility = View.GONE }
            item.breedName?.let {
                holder.textBreedName.visibility = View.VISIBLE
                holder.textBreedName.text = it
            } ?: kotlin.run { holder.textBreedName.visibility = View.GONE }
            item.lifespan?.let {
                holder.textLifeSpan.visibility = View.VISIBLE
                holder.textLifeSpan.text = it
            } ?: kotlin.run { holder.textLifeSpan.visibility = View.GONE }
            item.temperament?.let {
                holder.textTemperament.visibility = View.VISIBLE
                holder.textTemperament.text = it
            } ?: kotlin.run { holder.textTemperament.visibility = View.GONE }
        }
    }
}