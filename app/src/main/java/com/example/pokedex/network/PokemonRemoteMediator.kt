package com.example.pokedex.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.RemoteKeys
import com.example.pokedex.persistence.AppDatabase
import com.example.pokedex.persistence.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val pokemonApiService: PokemonApiService
) : RemoteMediator<Int, Pokemon>() {
    private val STARTING_PAGE_INDEX = 0
    private val pokemonDao = database.pokemonDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Pokemon>): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        try {
            Log.d("페이지값",page.toString())
            val response = pokemonApiService.getPokemonList(state.config.pageSize,page*20)
            val endOfList = !response.isSuccessful
            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    remoteKeyDao.clearAll()
                    pokemonDao.clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page-1
                val nextKey = if(endOfList) null else page+1
                val keys = response.body()?.results?.map {
                    RemoteKeys(it.name,prevKey,nextKey)
                }
                if (keys != null) {
                    remoteKeyDao.insertRemote(keys)
                }
                response.body()?.let { pokemonDao.insertPokemonList(it.results) }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        }catch (e:IOException){
            return   MediatorResult.Error(e)
        }catch (e:HttpException){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Pokemon>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { pokemon: Pokemon -> remoteKeyDao.getRemoteKeys(pokemon.name) }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .lastOrNull { it.data.isNotEmpty() }
                ?.data?.lastOrNull()
                ?.let { pokemon: Pokemon -> remoteKeyDao.getRemoteKeys(pokemon.name) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.name?.let {
                    remoteKeyDao.getRemoteKeys(it)
                }
            }
        }
    }

}