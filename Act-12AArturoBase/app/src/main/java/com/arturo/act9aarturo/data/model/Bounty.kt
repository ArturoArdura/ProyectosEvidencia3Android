package com.arturo.act9aarturo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Bounty(
    val id: Long = 0,
    val title: String,
    val reward_points: Int,
    val icon_res: String,
    var isCompleted: Boolean = false // Estado local para la UI
)