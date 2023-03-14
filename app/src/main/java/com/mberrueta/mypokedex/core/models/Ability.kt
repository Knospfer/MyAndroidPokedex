package com.mberrueta.mypokedex.core.models

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)