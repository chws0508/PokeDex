package com.example.pokedex.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokedex.model.Pokemon
import com.example.pokedex.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {
    private val _pokemonData = MutableLiveData<PagingData<Pokemon>>()

    val pokemonData: LiveData<PagingData<Pokemon>> get() = _pokemonData

     @OptIn(ExperimentalPagingApi::class)
     fun fetchPokemonList(): Flow<PagingData<Pokemon>> {
        return mainRepository.getAllPokemon(viewModelScope)
    }
}