package com.example.pokemoncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ccink.resources.PokemonComposeTheme
import com.example.pokemoncompose.navigation.RootNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PokemonComposeTheme {
                RootNavGraph(navController = rememberNavController())
            }
        }
    }
}
