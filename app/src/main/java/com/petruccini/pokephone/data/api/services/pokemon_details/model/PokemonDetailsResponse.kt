package com.petruccini.pokephone.data.api.services.pokemon_details.model

import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.entities.Sprites

data class PokemonDetailsResponse(
    val base_experience: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: SpritesResponse,
    val types: List<Type>,
    val weight: Int
)

fun PokemonDetailsResponse.toPokemonDetails() = PokemonDetails(
    id = id,
    name = name,
    height = height,
    weight = weight,
    types = types.map { it.type.name },
    sprites = Sprites(
        frontDefault = sprites.front_default,
        backDefault = sprites.back_default
    )
)

data class SpritesResponse(
    val back_default: String,
    val back_shiny: String,
    val front_default: String,
    val front_shiny: String,
)

data class Type(
    val slot: Int,
    val type: TypeX
)

data class TypeX(
    val name: String,
    val url: String
)