package com.deathhit.feature.thumbnail.fragment.thumbnail_info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.deathhit.feature.thumbnail.databinding.ItemThumbnailInfoBreedBinding
import com.deathhit.feature.thumbnail.model.Breed

class BreedAdapter : ListAdapter<Breed, BreedViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR =
            object : DiffUtil.ItemCallback<Breed>() {
                override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean =
                    oldItem.breedId == newItem.breedId

                override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean =
                    oldItem == newItem
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder =
        BreedViewHolder(
            ItemThumbnailInfoBreedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

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