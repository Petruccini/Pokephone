package com.petruccini.pokephone.data.api.services.pokemon_details.model

import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.entities.Sprites

data class PokemonDetailsApiModel(
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: SpritesApiModel,
    val types: List<TypeApiModel>,
    val weight: Int
)

fun PokemonDetailsApiModel.toPokemonDetails() = PokemonDetails(
    id = id,
    name = name,
    height = height,
    weight = weight,
    order = order,
    types = types.map { it.type.name },
    sprites = Sprites(
        frontDefault = sprites.front_default,
        backDefault = sprites.back_default
    )
)

data class SpritesApiModel(
    val back_default: String,
    val back_shiny: String,
    val front_default: String,
    val front_shiny: String,
)

data class TypeApiModel(
    val slot: Int,
    val type: TypeXApiModel
)

data class TypeXApiModel(
    val name: String,
    val url: String
)