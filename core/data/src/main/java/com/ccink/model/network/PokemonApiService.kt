package com.ccink.model.network

import com.ccink.model.dto.Pokemon
import com.ccink.model.dto.Shortcut
import com.ccink.model.model.BaseModel
import com.ccink.model.model.ItemRequest
import retrofit2.Response
import javax.inject.Inject

interface PokemonApiService {
    suspend fun readPokemonShortcut(request: ItemRequest): Response<BaseModel<List<Shortcut>>>
    suspend fun readPokemon(pokeName: String): Response<Pokemon>
}

internal class PokemonApiServiceImpl @Inject constructor(
    private val pokemonApi: PokemonApi
) : PokemonApiService {
    override suspend fun readPokemonShortcut(request: ItemRequest): Response<BaseModel<List<Shortcut>>> =
        pokemonApi.readPokemonShortcut(
            offset = request.offset,
            limit = request.limit
        )

    override suspend fun readPokemon(pokeName: String): Response<Pokemon> =
        pokemonApi.readPokemon(pokeName = pokeName)
}