package com.mberrueta.mypokedex.screens.pokemonlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonListScreen() {
    Scaffold(
        topBar = { TopAppBar(backgroundColor = Color.Transparent) {} },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                PageContent()
            }
        },
    )
}

@Composable
fun PageContent(viewModel: PokemonListScreenViewModel = hiltViewModel()) {
    val pokemonList by remember { viewModel.completePokemonList }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Pokedex", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(pokemonList.size) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                ) {
                    Text(text = pokemonList[index].name)
                }

            }
        })
    }
}