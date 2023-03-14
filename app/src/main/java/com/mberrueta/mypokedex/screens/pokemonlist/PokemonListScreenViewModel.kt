package com.mberrueta.mypokedex.screens.pokemonlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}