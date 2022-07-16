package com.example.pokedex.repository

import com.example.pokedex.model.Pokemon
import com.example.pokedex.network.PokemonApiService
import com.example.pokedex.persistence.PokemonDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    private val pokemonDao: PokemonDao,
) :Repository{

    suspend fun fetchPokemonList(): List<Pokemon>? {
       val pokemonList = pokemonApiService.getPokemonList(20,0).body()?.results
        if (pokemonList != null) {
            pokemonDao.insertPokemonList(pokemonList)
        }
        return pokemonList
    }
}