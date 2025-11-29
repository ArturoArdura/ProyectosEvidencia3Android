package com.arturo.act9aarturo.models

import kotlinx.serialization.Serializable

@Serializable
data class Alien(
    val id: Long = 0,
    val name: String,
    val species: String
)