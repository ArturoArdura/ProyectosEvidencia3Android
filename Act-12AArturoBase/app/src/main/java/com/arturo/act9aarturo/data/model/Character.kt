package com.arturo.act9aarturo.data.model

// Modelo simplificado para la API de Rick & Morty (solo estética)
data class Character(
    val id: Int,
    val name: String,
    val image: String
)

data class CharacterResponse(val results: List<Character>)