package com.deathhit.domain.model

import com.deathhit.domain.database.entity.BreedEntity

class BreedDO(
    breedId: String,
    bredFor: String?,
    breedGroup: String?,
    breedName: String?,
    lifespan: String?,
    temperament: String?
) : BreedEntity(
    breedId,
    bredFor,
    breedGroup,
    breedName,
    lifespan,
    temperament
)