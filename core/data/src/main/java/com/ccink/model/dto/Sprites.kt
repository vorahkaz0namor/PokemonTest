package com.ccink.model.dto

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default")
    val image: String
)