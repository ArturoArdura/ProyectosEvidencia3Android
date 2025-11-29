package com.arturo.act9aarturo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DimensionLog(
    val id: Long = 0,
    val venue_name: String,
    val visit_date: String, // Simplificado como string ISO
    val points_earned: Int
)