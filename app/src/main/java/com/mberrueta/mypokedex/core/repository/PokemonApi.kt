package com.mberrueta.mypokedex.core.repository

import com.mberrueta.mypokedex.core.models.Pokemon
import com.mberrueta.mypokedex.core.models.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonListPaginated(
        @Query("limit") limint: Int,
        @Query("offset") offset: Int,
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): Pokemon
}