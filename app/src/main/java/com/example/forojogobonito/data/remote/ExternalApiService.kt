package com.example.forojogobonito.data.remote

import com.example.forojogobonito.model.PartidosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExternalApiService {
    //Pedimos próximos 15 partidos de la Premier League
    @GET("api/v1/json/3/eventsnextleague.php")
    suspend fun getProximosPartidos(
        @Query("id") idLiga: String = "4328"
    ): PartidosResponse
}