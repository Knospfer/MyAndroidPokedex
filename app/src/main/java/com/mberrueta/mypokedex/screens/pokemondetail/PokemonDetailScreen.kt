package com.mberrueta.mypokedex.screens.pokemondetail

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    Box {
        Text(text = pokemonName)

    }
}