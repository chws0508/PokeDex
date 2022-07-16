package com.example.pokedex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {
    private val _pokemonList = MutableLiveData<MutableList<Pokemon>>()

    val pokemonList: LiveData<MutableList<Pokemon>> get() = _pokemonList

    init {
        fetchPokemonList()
    }

     private fun fetchPokemonList(){
        viewModelScope.launch(dispatcherIO) {
            _pokemonList.postValue((mainRepository.fetchPokemonList() as MutableList<Pokemon>?))
        }
    }
}