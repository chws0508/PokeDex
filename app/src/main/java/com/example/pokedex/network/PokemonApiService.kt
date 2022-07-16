package com.example.pokedex.network

import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int=20,
        @Query("offset") offset: Int=0
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String
    ):Response<PokemonInfo>

}