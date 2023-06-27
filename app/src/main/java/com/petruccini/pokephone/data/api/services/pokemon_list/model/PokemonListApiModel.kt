package com.petruccini.pokephone.data.api.services.pokemon_list.model

import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList

data class PokemonListApiModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonApiModel>
)

data class PokemonApiModel(
    val name: String,
    val url: String
)

fun PokemonListApiModel.toPokemonList() = PokemonList(
    count = count,
    pokemonItems = results.map { PokemonItem(
        id = it.url.split("/").filter { it.isNotBlank() }.last().toInt(),
        name = it.name
    ) }
)