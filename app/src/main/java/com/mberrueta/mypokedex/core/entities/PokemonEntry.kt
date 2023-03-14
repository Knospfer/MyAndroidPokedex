package com.mberrueta.mypokedex.core.entities

import com.mberrueta.mypokedex.core.constants.Constants
import java.util.*

class PokemonEntry(unparsedName: String, index: Int) {
    val name: String
    val urlImage: String
    val number: Int

    init {
        number = index + 1
        urlImage = "${Constants.SPRITE_URL}/$number.png"
        name = unparsedName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT)
            else it.toString()
        }
    }

}