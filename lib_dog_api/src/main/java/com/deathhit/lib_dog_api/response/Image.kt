package com.deathhit.lib_dog_api.response

data class Image(
    val breeds: List<Breed>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) {
    data class Breed(
        val weight: Weight,
        val height: Height,
        val id: String,
        val name: String,
        val bred_for: String,
        val breed_group: String,
        val life_span: String,
        val temperament: String,
        val reference_image_id: String
    ) {
        data class Height(val imperial: String, val metric: String)
        data class Weight(val imperial: String, val metric: String)
    }
}