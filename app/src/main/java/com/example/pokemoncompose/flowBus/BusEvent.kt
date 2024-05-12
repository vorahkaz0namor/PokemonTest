package com.example.pokemoncompose.flowBus

interface BusEvent

sealed interface PokemonEvent : BusEvent {
    data object LoadData: PokemonEvent
    data class ShowItemCard(val name: String): PokemonEvent
    data object BackToPokemonMainScreen: PokemonEvent
}