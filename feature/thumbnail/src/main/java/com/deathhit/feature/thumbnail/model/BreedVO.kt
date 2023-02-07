package com.deathhit.feature.thumbnail.model

import com.deathhit.data.breed.BreedDO

data class BreedVO(
    val breedId: String,
    val bredFor: String?,
    val breedGroup: String?,
    val breedName: String?,
    val lifespan: String?,
    val temperament: String?
)

fun BreedDO.toBreedVO() = BreedVO(breedId, bredFor, breedGroup, breedName, lifespan, temperament)
