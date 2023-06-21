package com.petruccini.pokephone.domain.entities

data class PokemonList(
    val count: Int,
    val results: List<PokemonItem>
)

data class PokemonItem(
    val id: Int,
    val name: String
)