package com.example.pokemoncompose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
            navigation(route = Graph.MAIN, startDestination = MainScreen.Main.route) {
                composable(route = MainScreen.Main.route) {
                    PokemonNavHost()
                }
            }
}

sealed class MainScreen(val route: String) {

    companion object {
        const val MAIN_SCREEN = "main_screen"
    }

    data object Main : MainScreen(MAIN_SCREEN)
}