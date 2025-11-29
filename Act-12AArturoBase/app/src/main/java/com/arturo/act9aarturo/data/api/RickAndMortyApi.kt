package com.arturo.act9aarturo.data.api

import com.arturo.act9aarturo.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    // Traemos personajes aleatorios
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse
}