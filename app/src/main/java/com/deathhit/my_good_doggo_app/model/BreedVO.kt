package com.deathhit.my_good_doggo_app.model

import android.os.Parcelable
import com.deathhit.domain.model.BreedDO
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreedVO(
    val breedId: String,
    val bredFor: String?,
    val breedGroup: String?,
    val breedName: String?,
    val lifespan: String?,
    val temperament: String?
) : Parcelable {
    companion object {
        fun valueOf(breedDO: BreedDO) = BreedVO(
            breedDO.breedId,
            breedDO.bredFor,
            breedDO.breedGroup,
            breedDO.breedName,
            breedDO.lifespan,
            breedDO.temperament
        )
    }
}