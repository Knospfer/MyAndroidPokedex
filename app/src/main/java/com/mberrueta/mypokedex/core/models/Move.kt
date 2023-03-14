package com.mberrueta.mypokedex.core.models

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)