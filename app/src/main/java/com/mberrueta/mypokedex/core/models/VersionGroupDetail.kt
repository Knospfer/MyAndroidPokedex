package com.mberrueta.mypokedex.core.models

import com.mberrueta.mypokedex.core.models.MoveLearnMethod
import com.mberrueta.mypokedex.core.models.VersionGroup

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)