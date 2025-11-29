package com.arturo.act9aarturo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Reward(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val points: Int = 0, // Unificado a 'points'
    val is_redeemed: Boolean = false
)