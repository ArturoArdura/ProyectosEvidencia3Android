package com.arturo.act9aarturo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    val id: Long = 0,
    val name: String,
    val description: String,
    val image_url: String? = null
)