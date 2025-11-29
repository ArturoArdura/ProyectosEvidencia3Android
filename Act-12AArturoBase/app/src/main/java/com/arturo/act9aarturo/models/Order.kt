package com.arturo.act9aarturo.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Long = 0,
    val alien_id: Long,
    val reward_id: Long
)

@Serializable
data class OrderWithDetails(
    val id: Long,
    val aliens: Alien?,
    val rewards: Reward?
)