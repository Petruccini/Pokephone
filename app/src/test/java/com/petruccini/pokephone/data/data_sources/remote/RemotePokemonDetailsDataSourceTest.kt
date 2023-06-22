package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_details.model.PokemonDetailsResponse
import com.petruccini.pokephone.data.api.services.pokemon_details.PokemonDetailsService
import com.petruccini.pokephone.data.api.services.pokemon_details.model.SpritesResponse
import com.petruccini.pokephone.data.api.services.pokemon_details.model.Type
import com.petruccini.pokephone.data.api.services.pokemon_details.model.TypeX
import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.entities.Sprites
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RemotePokemonDetailsDataSourceTest {

    private val pokemonDetailsService: PokemonDetailsService = mock()
    private val remotePokemonDetailsDataSource =
        RemotePokemonDetailsDataSource(pokemonDetailsService)

    @Test
    fun fetchPokemonDetails_ShouldReturnPokemonDetails_WhenSuccess() = runTest {
        // Given
        val pokemonName = "bulbasaur"
        val response = PokemonDetailsResponse(
            name = pokemonName,
            id = 1,
            height = 7,
            weight = 69,
            types = listOf(
                Type(slot = 1, type = TypeX(name = "grass", url = "")),
                Type(slot = 2, type = TypeX(name = "poison", url = ""))
            ),
            base_experience = 64,
            order = 1,
            sprites = SpritesResponse(
                front_default = "pokemon/1.png",
                back_default = "pokemon/back/1.png",
                front_shiny = "",
                back_shiny = ""
            )
        )
        val expected = PokemonDetails(
            name = pokemonName,
            id = 1,
            height = 7,
            weight = 69,
            types = listOf("grass", "poison"),
            sprites = Sprites(
                frontDefault = "pokemon/1.png",
                backDefault = "pokemon/back/1.png",
            )
        )
        `when`(pokemonDetailsService.getPokemonDetails(pokemonName)).thenReturn(
            Response.success(
                response
            )
        )

        // When
        val result = remotePokemonDetailsDataSource.fetchPokemonDetails(pokemonName)

        // Then
        result.collect {
            assertEquals(expected, it)
        }
    }

    @Test
    fun fetchPokemonDetails_ShouldReturnNull_WhenFailure() = runTest {
        // Given
        val pokemonName = "bulbasaur"
        `when`(pokemonDetailsService.getPokemonDetails(pokemonName)).thenReturn(
            Response.error(404, ResponseBody.create(null, ""))
        )

        // When
        val result = remotePokemonDetailsDataSource.fetchPokemonDetails(pokemonName)

        // Then
        result.collect {
            assertNull(it)
        }
    }
}