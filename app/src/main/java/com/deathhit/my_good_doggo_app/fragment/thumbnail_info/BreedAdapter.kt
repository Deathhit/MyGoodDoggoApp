package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.deathhit.my_good_doggo_app.model.BreedVO

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
            with(holder.binding.textViewBredFor) {
                item.bredFor?.let {
                    visibility = View.VISIBLE
                    text = it
                } ?: { visibility = View.GONE }
            }

            with(holder.binding.textViewBreedGroup) {
                item.breedGroup?.let {
                    visibility = View.VISIBLE
                    text = it
                } ?: { visibility = View.GONE }
            }

            with(holder.binding.textViewBreedName) {
                item.breedName?.let {
                    visibility = View.VISIBLE
                    text = it
                } ?: { visibility = View.GONE }
            }

            with(holder.binding.textViewLifespan) {
                item.lifespan?.let {
                    visibility = View.VISIBLE
                    text = it
                } ?: { visibility = View.GONE }
            }

            with(holder.binding.textViewTemperament) {
                item.temperament?.let {
                    visibility = View.VISIBLE
                    text = it
                } ?: { visibility = View.GONE }
            }
        }
    }
}