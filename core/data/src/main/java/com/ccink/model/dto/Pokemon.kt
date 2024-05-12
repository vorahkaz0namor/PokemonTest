package com.ccink.model.dto

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val experience: Int,
    val height: Int,
    val weight: Int,
    val sprites: Sprites
) {
    override fun toString(): String =
        """
                id = $id
                name = $name
                experience = $experience
                height = $height
                weight = $weight
                image = ${sprites.image}
            """.trimIndent()
}