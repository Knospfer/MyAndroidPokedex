package com.mberrueta.mypokedex.screens.pokemondetail

import androidx.lifecycle.ViewModel
import com.mberrueta.mypokedex.core.definitions.Resource
import com.mberrueta.mypokedex.core.models.Pokemon
import com.mberrueta.mypokedex.core.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
    suspend fun getPokemonDetail(name: String): Resource<Pokemon> {
        return repository.getPokemonDetail(name)
    }
}