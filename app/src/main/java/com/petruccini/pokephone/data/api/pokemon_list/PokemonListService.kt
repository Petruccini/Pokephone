package com.petruccini.pokephone.data.api.pokemon_list


import com.petruccini.pokephone.data.api.pokemon_list.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val POKEMON_LIST_SERVICE_URL: String = "/api/v2/pokemon/?"
interface PokemonListService {

    @GET(POKEMON_LIST_SERVICE_URL)
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonListResponse>
}