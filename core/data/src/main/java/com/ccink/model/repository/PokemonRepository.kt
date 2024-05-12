package com.ccink.model.repository

import com.ccink.model.dto.Pokemon
import com.ccink.model.dto.Shortcut
import com.ccink.model.handleAPICall
import com.ccink.model.handleRootCall
import com.ccink.model.model.ItemRequest
import com.ccink.model.model.Resource
import com.ccink.model.model.BaseModel
import com.ccink.model.network.PokemonApiService
import javax.inject.Inject

interface PokemonRepository {
    suspend fun readPokemons(request: ItemRequest): Resource<BaseModel<List<Pokemon>>>
}

internal class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService
): PokemonRepository {
    override suspend fun readPokemons(request: ItemRequest): Resource<BaseModel<List<Pokemon>>> =
        handleRootCall {
            var pokeNames: List<String>
            var pokemons: List<Pokemon> = emptyList()
            handleAPICall {
                pokemonApiService.readPokemonShortcut(request).also { response ->
                    response.body()?.results?.let {
                        pokeNames = it.map(Shortcut::name)
                        if (pokeNames.isNotEmpty()) {
                            pokeNames.map {
                                pokemonApiService.readPokemon(it).also { response ->
                                    response.body()?.let {
                                        pokemons = pokemons + it
                                    }
                                }
                            }
                        }
                    }
                }
            }
            pokemons.ifEmpty { null }
        }
}