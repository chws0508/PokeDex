package com.example.pokedex.di

import android.app.Application
import androidx.room.Room
import com.example.pokedex.persistence.AppDatabase
import com.example.pokedex.persistence.PokemonDao
import com.example.pokedex.persistence.RemoteKeysDao
import com.example.pokedex.persistence.TypeResponseConverter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "PokemonDB"
            )
            .fallbackToDestructiveMigration()//데이터베이스 고쳐도 오류 안나게하는거
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(
        appDatabase: AppDatabase
    ): PokemonDao {
        return appDatabase.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(
        appDatabase: AppDatabase
    ): RemoteKeysDao {
        return appDatabase.remoteKeyDao()
    }
}