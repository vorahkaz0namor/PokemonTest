package com.example.pokemoncompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {
        mainNavGraph()
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
}