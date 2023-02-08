package com.deathhit.data.breed

import com.deathhit.core.database.model.BreedEntity

internal fun BreedEntity.toDO() =
    BreedDO(
        breedId = breedId,
        bredFor = bredFor,
        breedGroup = breedGroup,
        breedName = breedName,
        lifespan = lifespan,
        temperament = temperament
    )