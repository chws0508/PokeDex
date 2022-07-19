package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey
    val id:String,
    val prevKey:Int?,
    val nextKey:Int?
)