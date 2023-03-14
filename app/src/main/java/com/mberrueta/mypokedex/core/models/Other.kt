package com.mberrueta.mypokedex.core.models

import com.google.gson.annotations.SerializedName
import com.mberrueta.mypokedex.core.models.DreamWorld
import com.mberrueta.mypokedex.core.models.Home
import com.mberrueta.mypokedex.core.models.OfficialArtwork

data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)