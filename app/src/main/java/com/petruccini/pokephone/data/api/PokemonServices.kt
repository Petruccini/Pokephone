package com.petruccini.pokephone.data.api


import com.petruccini.pokephone.data.api.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val POKEMON_LIST_SERVICE_URL: String = "/api/v2/pokemon/?"

interface PokemonServices {

    @GET(POKEMON_LIST_SERVICE_URL)
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonListResponse>
}