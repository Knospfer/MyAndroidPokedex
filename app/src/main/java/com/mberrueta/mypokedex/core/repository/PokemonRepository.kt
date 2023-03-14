package com.mberrueta.mypokedex.core.repository

import com.mberrueta.mypokedex.core.definitions.Resource
import com.mberrueta.mypokedex.core.entities.PokemonEntry
import com.mberrueta.mypokedex.core.models.Pokemon
import com.mberrueta.mypokedex.core.models.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(private val api: PokemonApi) {
    suspend fun getAllPokemons(): Resource<List<PokemonEntry>> {
        val response = try {
            api.getPokemonListPaginated(10000, 0)
        } catch (e: Exception) {
            return Resource.Error(null, "An error occured")
        }
        return Resource.Success(parseResponse(response))
    }

    suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetail(name)
        } catch (e: Exception) {
            return Resource.Error(null, "An error occured")
        }
        return Resource.Success(response)
    }

    private fun parseResponse(response: PokemonList): List<PokemonEntry> {
        return response.results.mapIndexed { index, pokemon ->
            PokemonEntry(pokemon.name, index)
        }
    }
}