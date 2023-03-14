package com.mberrueta.pokedexexample.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white") val blackWhite: BlackWhite
)