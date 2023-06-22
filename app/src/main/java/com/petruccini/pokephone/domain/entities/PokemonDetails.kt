package com.petruccini.pokephone.domain.entities

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val sprites: Sprites
)

data class Sprites(
    val frontDefault: String,
    val backDefault: String,
)