package com.deathhit.my_good_doggo_app.extensions

import com.deathhit.domain.model.BreedDO
import com.deathhit.my_good_doggo_app.model.BreedVO

fun BreedDO.toVO() = BreedVO(breedId, bredFor, breedGroup, breedName, lifespan, temperament)