package com.mberrueta.mypokedex.core.models

import com.google.gson.annotations.SerializedName
import com.mberrueta.pokedexexample.data.remote.responses.BlackWhite

data class GenerationV(
    @SerializedName("black-white") val blackWhite: BlackWhite
)