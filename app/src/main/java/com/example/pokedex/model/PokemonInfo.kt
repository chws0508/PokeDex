package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "pokemonInfo")
@JsonClass(generateAdapter = true)
data class PokemonInfo(
    @field:Json(name = "id")  @PrimaryKey val id: Int,
    @field:Json(name = "base_experience") val base_experience:Int,
    @field:Json(name = "name") val name:String,
    @field:Json(name = "height") val height:Int,
    @field:Json(name = "weight") val weight:Int,
    @field:Json(name = "types") val type:List<TypeResponse>,
) {

    @JsonClass(generateAdapter = true)
    data class TypeResponse(
        @field:Json(name = "slot") val slot: Int,
        @field:Json(name = "type") val type: Type
    )

    @JsonClass(generateAdapter = true)
    data class Type(
        @field:Json(name = "name") val name: String,
        @field:Json(name="url") val url:String
    )
}