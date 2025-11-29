package com.arturo.act9aarturo.models

import kotlinx.serialization.Serializable

@Serializable
data class Reward(
    val id: Long = 0,
    val title: String,
    val points: Int
)