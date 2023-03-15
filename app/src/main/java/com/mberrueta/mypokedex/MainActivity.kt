package com.mberrueta.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mberrueta.mypokedex.core.theme.MyPokedexTheme
import com.mberrueta.mypokedex.screens.pokemondetail.PokemonDetailScreen
import com.mberrueta.mypokedex.screens.pokemonlist.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen") {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController)
                    }
                    composable(
                        "pokemon_detail_screen/{pokemonName}",
                        arguments = listOf(
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        ),
                    ) {
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName") ?: "Missigno"
                        }

                        PokemonDetailScreen(pokemonName)
                    }
                }
            }
        }
    }
}
