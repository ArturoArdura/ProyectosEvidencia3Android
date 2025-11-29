package com.arturo.act9aarturo.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    // REEMPLAZA ESTO CON TUS DATOS DE SUPABASE.COM
    private const val SUPABASE_URL = "https://efmpwlhtphjrmodsayqy.supabase.co"
    private const val SUPABASE_KEY = "sb_secret_oynPCFH5gpz8n7Gn2PjxjA_UrdwGAYf"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}