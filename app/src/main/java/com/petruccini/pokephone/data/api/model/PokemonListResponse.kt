package com.petruccini.pokephone.data.api.model

import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItemResponse>
)

data class PokemonItemResponse(
    val name: String,
    val url: String
)

fun PokemonListResponse.toPokemonList() = PokemonList(
    count = count,
    pokemonItems = results.map { it.toPokemonItem() }
)

fun PokemonItemResponse.toPokemonItem() = PokemonItem(
    id = url.split("/").filter { it.isNotBlank() }.last().toInt(),
    name = name
)