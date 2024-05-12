package com.example.pokemoncompose.ui

import androidx.compose.runtime.Composable
import com.ccink.model.dto.Pokemon

data class PokeState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val handlerMessage: String? = null,
    val pokemonList: List<Pokemon> = emptyList(),
    val selectedItem: Pokemon? = null
) {
    override fun toString(): String =
        "loading = $loading\n" +
                "error = $error\n" +
                "pokemons = ${pokemonList.size}\n" +
                "selectedItem = $selectedItem\n"
}

fun PokeState.loading() = copy(
    loading = true,
    error = false
)

fun PokeState.error(message: String?) = copy(
    loading = false,
    error = true,
    handlerMessage = message
)

fun PokeState.display() = copy(
    loading = false,
    error = false
)

@Composable
fun PokeState.HandlingLoadState(
    composeAfterSuccess: @Composable () -> Unit
) {
        when {
            loading -> ProgressBar()
            error -> ErrorDialog(message = handlerMessage)
            else -> composeAfterSuccess()
        }
}
