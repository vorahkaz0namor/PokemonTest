package com.example.pokemoncompose.ui

data class CallbackState(
    val onBackButtonClick: () -> Unit,
    val onSelectedItemClick: (String) -> Unit
)
