package com.example.pokemoncompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemoncompose.ui.PokemonDetailScreen
import com.example.pokemoncompose.ui.PokemonListScreen
import com.example.pokemoncompose.ui.WithCallbackState
import com.example.pokemoncompose.ui.PokemonViewModel

@Composable
fun PokemonNavHost(
    pokemonNavHostController: NavHostController = rememberNavController(),
    pokemonViewModel: PokemonViewModel = viewModel<PokemonViewModel>()
) {
    pokemonNavHostController
        .HelperForPokemonViewModel { navigateUp, navigateToDetailScreen ->
            pokemonViewModel.WithCallbackState(
                navigateUp = navigateUp,
                navigateToDetailScreen = navigateToDetailScreen
            ) { pokeState, callbackState ->
                NavHost(
                    navController = pokemonNavHostController,
                    startDestination = PokemonScreen.PokemonList.route
                ) {
                    composable(route = PokemonScreen.PokemonList.route) {
                        PokemonListScreen(
                            pokemonList = pokeState.pokemonList,
                            onSelectedItemClick = callbackState.onSelectedItemClick
                        )
                    }
                    composable(route = PokemonScreen.PokemonDetail.route) {
                        pokeState.selectedItem?.let {
                            PokemonDetailScreen(
                                pokemon = it,
                                onBackButtonClick = callbackState.onBackButtonClick
                            )
                        }
                    }
                }
            }
        }
}

sealed class PokemonScreen(val route: String) {

    companion object {
        const val LIST_SCREEN = "list_screen"
        const val DETAIL_SCREEN = "detail_screen"
    }

    data object PokemonList : PokemonScreen(LIST_SCREEN)
    data object PokemonDetail : PokemonScreen(DETAIL_SCREEN)
}

@Composable
fun NavHostController.HelperForPokemonViewModel(
    composable: @Composable (() -> Boolean, () -> Unit) -> Unit
) {
    val navigateUp = { navigateUp() }
    val navigateToDetailScreen = { navigateWithPopUpTo(route = PokemonScreen.PokemonDetail.route) }
    composable(navigateUp, navigateToDetailScreen)
}

fun NavHostController.navigateWithPopUpTo(route: String) {
    navigate(route = route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}