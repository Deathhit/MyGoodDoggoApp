package com.deathhit.data.thumbnail

import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.core.dog_api.model.Image

internal fun Image.toThumbnailEntity() = ThumbnailEntity(thumbnailId = id, thumbnailUrl = url)

internal fun Image.Breed.toBreedEntity() = BreedEntity(
    breedId = id,
    bredFor = bred_for,
    breedGroup = breed_group,
    breedName = name,
    lifespan = life_span,
    temperament = temperament
)

internal fun ThumbnailEntity.toDO() =
    ThumbnailDO(thumbnailId = thumbnailId, thumbnailUrl = thumbnailUrl)