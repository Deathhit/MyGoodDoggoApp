package com.deathhit.my_good_doggo_app.model

import android.os.Parcelable
import com.deathhit.core.model.BreedDO
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