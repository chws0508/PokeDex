package com.example.pokedex.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.model.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//primaryKey가 겹치는것이 있으면 덮어쓰겠다.
    suspend fun insertPokemonList(pokemonList:List<Pokemon>)

    @Query("SELECT * FROM Pokemon")
    suspend fun getAllPokemonList():List<Pokemon>
}