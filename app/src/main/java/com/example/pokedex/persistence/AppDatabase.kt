package com.example.pokedex.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.model.RemoteKeys

@Database(entities = [Pokemon::class,RemoteKeys::class], version = 298)
@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase:RoomDatabase() {
    abstract fun pokemonDao():PokemonDao
    abstract fun remoteKeyDao():RemoteKeysDao
}