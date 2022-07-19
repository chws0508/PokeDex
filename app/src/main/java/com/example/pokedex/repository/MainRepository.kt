package com.example.pokedex.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.pokedex.model.Pokemon
import com.example.pokedex.network.PokemonApiService
import com.example.pokedex.network.PokemonRemoteMediator
import com.example.pokedex.persistence.AppDatabase
import com.example.pokedex.persistence.PokemonDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val database: AppDatabase,
    private val pokemonApiService: PokemonApiService,
    private val pokemonDao: PokemonDao,
) :Repository{

    @ExperimentalPagingApi
    fun getAllPokemon(coroutineScope:CoroutineScope) : Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(20,enablePlaceholders = false),
        pagingSourceFactory = {pokemonDao.getAllPokemon()},
        remoteMediator = PokemonRemoteMediator(database,pokemonApiService)
    ).flow.cachedIn(coroutineScope)
}