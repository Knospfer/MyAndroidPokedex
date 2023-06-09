package com.mberrueta.mypokedex.screens.pokemondetail

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mberrueta.mypokedex.core.definitions.Resource
import com.mberrueta.mypokedex.core.models.Pokemon
import com.mberrueta.mypokedex.core.models.Stat
import com.mberrueta.mypokedex.core.utils.parseStatToAbbr
import com.mberrueta.mypokedex.core.utils.parseStatToColor
import com.mberrueta.mypokedex.core.utils.parseTypeToColor
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val apiResponse = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonDetail(pokemonName)
    }.value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
            )
        }
    ) {
        if (apiResponse is Resource.Success<Pokemon> && apiResponse.data != null) {
            DetailView(apiResponse.data)
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun DetailView(pokemon: Pokemon) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, orientation = Orientation.Vertical)
    ) {
        Text(
            text = pokemon.name.capitalize(Locale.ROOT),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "#${String.format("%04d", pokemon.id)}",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TypeSection(
            pokemon = pokemon, modifier = Modifier.padding(horizontal = 16.dp)
        )
        AsyncImage(
            model = pokemon.sprites.front_default,
            contentDescription = pokemon.name,
            modifier = Modifier.size(200.dp)
        )
        PokemonBaseStats(pokemonInfo = pokemon)
    }
}


@Composable
fun TypeSection(pokemon: Pokemon, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        for (type in pokemon.types) {
            Box(modifier = Modifier.clip(RoundedCornerShape(20.dp))) {
                Box(
                    modifier = Modifier.background(parseTypeToColor(type)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = type.type.name.capitalize(),
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animationDelay: Int = 100,
) {
    val maxBaseState = remember {
        pokemonInfo.stats.maxOf { it.base_stat }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Base stats:", fontSize = 20.sp, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]

            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.base_stat,
                statMaxValue = maxBaseState,
                statColor = parseStatToColor(stat),
                animationDelay = i * animationDelay,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
) {
    var animationPLayed by remember {
        mutableStateOf(false)
    }
    val currentPercent =
        animateFloatAsState(
            targetValue = if (animationPLayed) {
                statValue / statMaxValue.toFloat()
            } else 0f,
            animationSpec = tween(
                animationDuration,
                animationDelay
            )
        )
    LaunchedEffect(key1 = true) { animationPLayed = true }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xff505050)
                } else Color.LightGray
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(currentPercent.value) //è questa la cosa che si animerà
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = statName, fontWeight = FontWeight.Bold)
            Text(
                text = (currentPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}