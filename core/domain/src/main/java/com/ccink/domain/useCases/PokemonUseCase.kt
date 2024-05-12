package com.ccink.domain.useCases

import com.ccink.domain.di.IoDispatcher
import com.ccink.domain.getResponse
import com.ccink.model.dto.Pokemon
import com.ccink.model.model.BaseModel
import com.ccink.model.model.ItemRequest
import com.ccink.model.model.Resource
import com.ccink.model.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface PokemonUseCase {
    suspend fun readPokemons(): Resource<BaseModel<List<Pokemon>>>
}

internal class PokemonUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): PokemonUseCase {
    override suspend fun readPokemons(): Resource<BaseModel<List<Pokemon>>> =
        repository.readPokemons(request = ItemRequest()).getResponse(dispatcher)
}