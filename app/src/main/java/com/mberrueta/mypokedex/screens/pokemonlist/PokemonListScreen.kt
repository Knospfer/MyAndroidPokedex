package com.mberrueta.mypokedex.screens.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mberrueta.mypokedex.core.entities.PokemonEntry

@Composable
fun PokemonListScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(backgroundColor = Color.Transparent) {} },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                PageContent(navController)
            }
        },
    )
}

@Composable
fun PageContent(
    navController: NavController,
    viewModel: PokemonListScreenViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.completePokemonList }

    Column {
        Text(
            text = "Pokedex",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 8.dp),
            columns = GridCells.Fixed(2),
            content = {
                items(pokemonList.size) { index ->
                    PokemonCard(pokemonList[index], navController)
                }
            })
    }
}

@Composable
fun PokemonCard(
    pokemonEntry: PokemonEntry,
    navController: NavController,
    viewModel: PokemonListScreenViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(dominantColor)
            .clickable {
                navController.navigate("pokemon_detail_screen/${pokemonEntry.name}")
            }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                model = pokemonEntry.urlImage,
                contentDescription = pokemonEntry.name,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                onSuccess = {
                    viewModel.getDominantColor(it.result.drawable) { color ->
                        dominantColor = color
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "#${pokemonEntry.number}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = pokemonEntry.name,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}