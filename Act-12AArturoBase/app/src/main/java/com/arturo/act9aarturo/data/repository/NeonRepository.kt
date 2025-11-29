package com.arturo.act9aarturo.data.repository

import com.arturo.act9aarturo.data.api.RickAndMortyApi
import com.arturo.act9aarturo.data.model.Character
import com.arturo.act9aarturo.data.model.Reward
import com.arturo.act9aarturo.data.model.Venue
import com.arturo.act9aarturo.data.supabase.SupabaseManager
import kotlin.random.Random

class NeonRepository(private val api: RickAndMortyApi) {

    // Simulación de sesión de usuario
    private var userPoints = 0
    private var currentVenue: Venue? = null
    private var hasSpunWheelToday = false

    suspend fun getRandomMascot(): Character? {
        return try {
            val randomPage = Random.nextInt(1, 42)
            val response = api.getCharacters(randomPage)
            response.results.randomOrNull()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getVenues(): List<Venue> {
        return try {
            SupabaseManager.getVenues()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Nueva lógica: Viaje Interdimensional
    suspend fun travelToDimension(): Pair<Reward, Venue> {
        if (hasSpunWheelToday) {
            throw IllegalStateException("Portal energy depleted! Come back tomorrow.")
        }

        // 1. Obtener un Venue aleatorio de Supabase
        val venues = getVenues()
        if (venues.isEmpty()) throw Exception("No dimensions found")
        val destination = venues.random()
        currentVenue = destination

        // 2. Calcular Bono de Bienvenida (Puntos)
        val pointsWon = Random.nextInt(100, 500)
        userPoints += pointsWon
        hasSpunWheelToday = true

        // 3. Crear Recompensa de "Arribo"
        val arrivalReward = Reward(
            title = "Arrival Bonus: ${destination.name}",
            description = "+$pointsWon Mega Seeds added to your wallet",
            points = pointsWon
        )
        
        try {
            SupabaseManager.saveReward(arrivalReward)
        } catch (e: Exception) { }

        return Pair(arrivalReward, destination)
    }
    
    fun getUserBalance(): Int = userPoints
    
    fun getCurrentVenue(): Venue? = currentVenue
    
    fun hasSpun(): Boolean = hasSpunWheelToday

    // Gastar puntos en una oferta
    fun redeemPoints(cost: Int): Boolean {
        return if (userPoints >= cost) {
            userPoints -= cost
            true
        } else {
            false
        }
    }

    suspend fun getMyRewards(): List<Reward> {
        return try {
            SupabaseManager.getMyRewards()
        } catch (e: Exception) {
            emptyList()
        }
    }
}