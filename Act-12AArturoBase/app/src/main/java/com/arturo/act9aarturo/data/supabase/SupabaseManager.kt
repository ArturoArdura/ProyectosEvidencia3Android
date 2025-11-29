package com.arturo.act9aarturo.data.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import com.arturo.act9aarturo.data.model.Reward
import com.arturo.act9aarturo.data.model.Venue
import com.arturo.act9aarturo.data.model.DimensionLog
import com.arturo.act9aarturo.data.model.Bounty

object SupabaseManager {
    private const val SUPABASE_URL = "https://efmpwlhtphjrmodsayqy.supabase.co"
    private const val SUPABASE_KEY = "sb_secret_oynPCFH5gpz8n7Gn2PjxjA_UrdwGAYf"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }

    suspend fun getVenues(): List<Venue> {
        return client.from("venues").select().decodeList<Venue>()
    }

    suspend fun saveReward(reward: Reward) {
        client.from("rewards").insert(reward) 
    }
    
    suspend fun getMyRewards(): List<Reward> {
        return client.from("rewards").select().decodeList<Reward>()
    }

    suspend fun logVisit(log: DimensionLog) {
        client.from("dimension_logs").insert(log)
    }

    suspend fun getVisitHistory(): List<DimensionLog> {
        return client.from("dimension_logs").select().decodeList<DimensionLog>()
    }

    suspend fun getVenueDeals(venueName: String): List<Reward> {
        return client.from("rewards").select().decodeList<Reward>().take(3) 
    }

    // --- NUEVO: Misiones ---
    suspend fun getBounties(): List<Bounty> {
        return client.from("bounties").select().decodeList<Bounty>()
    }
}