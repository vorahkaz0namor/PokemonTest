package com.ccink.model.network

import com.ccink.model.dto.Pokemon
import com.ccink.model.dto.Shortcut
import com.ccink.model.model.BaseModel
import retrofit2.Response
import retrofit2.http.*

interface PokemonApi {
    @GET("pokemon")
    suspend fun readPokemonShortcut(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<BaseModel<List<Shortcut>>>

    @GET("pokemon/{poke_name}")
    suspend fun readPokemon(
        @Path("poke_name") pokeName: String
    ): Response<Pokemon>
}