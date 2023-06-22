package com.petruccini.pokephone.data.api.services.pokemon_details

import com.petruccini.pokephone.data.api.services.pokemon_details.model.PokemonDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val POKEMON_DETAILS_SERVICE_URL: String = "/api/v2/pokemon/"
interface PokemonDetailsService {

    @GET("$POKEMON_DETAILS_SERVICE_URL{pokemon_name}")
    suspend fun getPokemonDetails(
        @Path(
            value = "pokemon_name",
            encoded = true
        ) pokemonName: String
    ): Response<PokemonDetailsResponse>
}