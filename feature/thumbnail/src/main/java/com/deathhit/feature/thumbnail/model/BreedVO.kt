package com.deathhit.feature.thumbnail.model

import android.os.Parcelable
import com.deathhit.data.breed.BreedDO
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreedVO(
    val breedId: String,
    val bredFor: String?,
    val breedGroup: String?,
    val breedName: String?,
    val lifespan: String?,
    val temperament: String?
) : Parcelable

fun BreedDO.toVO() = BreedVO(breedId, bredFor, breedGroup, breedName, lifespan, temperament)