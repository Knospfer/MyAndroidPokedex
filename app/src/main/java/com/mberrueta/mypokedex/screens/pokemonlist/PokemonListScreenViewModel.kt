package com.mberrueta.mypokedex.screens.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.mberrueta.mypokedex.core.definitions.Resource
import com.mberrueta.mypokedex.core.entities.PokemonEntry
import com.mberrueta.mypokedex.core.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    var completePokemonList = mutableStateOf<List<PokemonEntry>>(listOf())

    init {
        initCompletePokemonList()
    }

    private fun initCompletePokemonList() {
        viewModelScope.launch {
            val response = repository.getAllPokemons()

            if (response is Resource.Success<List<PokemonEntry>>) {
                completePokemonList.value = response.data!!
            }
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}