package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            bindTextBredFor(item, holder.binding.textViewBredFor)
            bindTextBreedGroup(item, holder.binding.textViewBreedGroup)
            bindTextBreedName(item, holder.binding.textViewBreedName)
            bindTextLifespan(item, holder.binding.textViewLifespan)
            bindTextTemperament(item, holder.binding.textViewTemperament)
        }
    }

    private fun bindTextBredFor(item: BreedVO, textBredFor: TextView) {
        item.bredFor?.let {
            textBredFor.visibility = View.VISIBLE
            textBredFor.text = it
        } ?: kotlin.run { textBredFor.visibility = View.GONE }
    }

    private fun bindTextBreedGroup(item: BreedVO, textBreedGroup: TextView) {
        item.breedGroup?.let {
            textBreedGroup.visibility = View.VISIBLE
            textBreedGroup.text = it
        } ?: kotlin.run { textBreedGroup.visibility = View.GONE }
    }

    private fun bindTextBreedName(item: BreedVO, textBreedName: TextView) {
        item.breedName?.let {
            textBreedName.visibility = View.VISIBLE
            textBreedName.text = it
        } ?: kotlin.run { textBreedName.visibility = View.GONE }
    }

    private fun bindTextLifespan(item: BreedVO, textLifespan: TextView) {
        item.lifespan?.let {
            textLifespan.visibility = View.VISIBLE
            textLifespan.text = it
        } ?: kotlin.run { textLifespan.visibility = View.GONE }
    }

    private fun bindTextTemperament(item: BreedVO, textTemperament: TextView) {
        item.temperament?.let {
            textTemperament.visibility = View.VISIBLE
            textTemperament.text = it
        } ?: kotlin.run { textTemperament.visibility = View.GONE }
    }
}