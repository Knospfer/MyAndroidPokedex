package com.mberrueta.mypokedex.core.models

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)